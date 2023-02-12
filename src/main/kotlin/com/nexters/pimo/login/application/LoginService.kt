package com.nexters.pimo.login.application

import com.nexters.pimo.common.dto.BaseTokenInfo
import com.nexters.pimo.common.exception.NotFoundDataException
import com.nexters.pimo.common.utils.AuthorizationUtil
import com.nexters.pimo.common.utils.CipherUtil
import com.nexters.pimo.login.application.port.`in`.JwtTokenUseCase
import com.nexters.pimo.login.application.port.`in`.TokenEncodeUseCase
import com.nexters.pimo.login.application.port.out.JwtTokenPort
import com.nexters.pimo.login.domain.TokenInfo
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

/**
 * @author yoonho
 * @since 2023.01.26
 */
@Service
class LoginService(
    private val jwtTokenPorts: List<JwtTokenPort>
): JwtTokenUseCase, TokenEncodeUseCase {

    override fun createToken(state: String, code: String): Mono<TokenInfo> =
        findSocialType(state)
            .createToken(code)

    override fun encode(state: String, token: String): Mono<BaseTokenInfo> =
        Mono.just(BaseTokenInfo(provider = state, accessToken = CipherUtil.encode(state, token)))

    override fun decode(token: String): Mono<BaseTokenInfo> =
        Mono.just(AuthorizationUtil.getTokenInfo(CipherUtil.decode(token)))

    override fun authToken(state: String, token: String): Mono<String> =
        findSocialType(state)
            .authToken(token)

    private fun findSocialType(state: String) =
        jwtTokenPorts.find { it.support(state) }
            ?: throw NotFoundDataException()
}