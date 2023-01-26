package com.nexters.pimo.login.application.port.out

import com.nexters.pimo.login.domain.TokenInfo
import reactor.core.publisher.Mono

/**
 * @author yoonho
 * @since 2023.01.26
 */
interface JwtTokenPort {
    fun createToken(code: String): Mono<TokenInfo>
    fun support(state: String): Boolean
}