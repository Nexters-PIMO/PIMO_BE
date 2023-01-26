package com.nexters.pimo.login.application

import com.nexters.pimo.common.exception.NotFoundDataException
import com.nexters.pimo.login.application.port.`in`.JwtTokenUseCase
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
): JwtTokenUseCase {

    override fun createToken(state: String, code: String): Mono<TokenInfo> =
        findSocialType(state)
            .createToken(code)

    private fun findSocialType(state: String) =
        jwtTokenPorts.find { it.support(state) }
            ?: throw NotFoundDataException()
}