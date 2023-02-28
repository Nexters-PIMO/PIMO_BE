package com.nexters.pimo.common.filter

import com.nexters.pimo.common.constants.CommCode
import com.nexters.pimo.common.dto.BaseTokenInfo
import com.nexters.pimo.common.exception.BadRequestException
import com.nexters.pimo.common.exception.UnAuthorizationException
import com.nexters.pimo.common.utils.CipherUtil
import com.nexters.pimo.login.application.port.`in`.JwtTokenUseCase
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

private const val TOKEN_PREFIX = "Bearer "
private const val USER_ID_ATTRIBUTE = "userId"
private const val DEV_TOKEN_PREFIX = "DEV "

/**
 * @author yoonho
 * @since 2023.02.15
 */
@Component
class AuthorizationFilter(
    private val jwtTokenUseCase: JwtTokenUseCase
): WebFilter {
    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val path = exchange.request.path.pathWithinApplication().value()

        // 로그인 관련 API 제외처리
        if(Regex("/login*") in path) {
            return chain.filter(exchange)
        }

        // Swagger 관련 API 제외처리
        if(Regex("/swagger*") in path || Regex("/v3/api-docs*") in path) {
            return chain.filter(exchange)
        }

        // Actuator (Health check) 관련 API 제외 처리
        if(Regex("/actuator*") in path) {
            return chain.filter(exchange)
        }

        val authorization = exchange.request.headers.getFirst(HttpHeaders.AUTHORIZATION)
            ?: throw UnAuthorizationException("Authorization 헤더가 존재하지 않습니다.")

        if(DEV_TOKEN_PREFIX in authorization) {
            val userId = authorization.substringAfter(DEV_TOKEN_PREFIX)
            exchange.attributes[USER_ID_ATTRIBUTE] = userId
            return chain.filter(exchange)
        }

        if(TOKEN_PREFIX in authorization) {
            val accessToken = CipherUtil.decode(authorization.substringAfter(TOKEN_PREFIX))
            val tokenInfo =
                when {
                    accessToken.startsWith(CommCode.Social.KAKAO.prefix) -> {
                        BaseTokenInfo(provider = CommCode.Social.KAKAO.code, accessToken = accessToken.substring(1))
                    }
                    accessToken.startsWith(CommCode.Social.APPLE.prefix) -> {
                        BaseTokenInfo(provider = CommCode.Social.APPLE.code, accessToken = accessToken.substring(1))
                    }
                    else -> throw UnAuthorizationException("유효한 토큰이 아닙니다.")
                }

            return jwtTokenUseCase.authToken(tokenInfo)
                        .flatMap {
                            exchange.attributes[USER_ID_ATTRIBUTE] = it
                            chain.filter(exchange)
                        }
        }

        throw UnAuthorizationException("필수값을 누락하였습니다.")
    }
}

fun ServerRequest.userId(): String = this.attribute(USER_ID_ATTRIBUTE).get().toString()