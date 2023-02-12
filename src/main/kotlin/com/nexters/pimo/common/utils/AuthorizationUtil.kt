package com.nexters.pimo.common.utils

import com.nexters.pimo.common.constants.CommCode
import com.nexters.pimo.common.dto.BaseTokenInfo
import com.nexters.pimo.common.exception.BadRequestException
import org.slf4j.LoggerFactory
import org.springframework.web.reactive.function.server.ServerRequest

/**
 * @author yoonho
 * @since 2023.02.14
 */
object AuthorizationUtil {
    private val log = LoggerFactory.getLogger(this::class.java)

    fun getAuthorization(request: ServerRequest): BaseTokenInfo {
        val authorizationList = request.headers().header("Authorization")
        if(authorizationList.isNullOrEmpty()) {
            throw BadRequestException("필수 헤더정보를 입력해주세요")
        }
        val authorization = authorizationList.get(0)
        if(!authorization.uppercase().startsWith("BEARER") || authorization.length < 7){
            throw BadRequestException("헤더 포맷이 올바르지 않습니다")
        }

        val accessToken = CipherUtil.decode(authorization.substring(7))

        return this.getTokenInfo(accessToken)
    }

    fun getTokenInfo(token: String) =
        when {
            token.startsWith(CommCode.Social.KAKAO.prefix) -> {
                BaseTokenInfo(provider = CommCode.Social.KAKAO.code, accessToken = token.substring(1))
            }
            token.startsWith(CommCode.Social.APPLE.prefix) -> {
                BaseTokenInfo(provider = CommCode.Social.APPLE.code, accessToken = token.substring(1))
            }
            else -> throw BadRequestException("유효한 토큰이 아닙니다.")
        }

}