package com.nexters.pimo.login.application.port.`in`

import com.nexters.pimo.common.dto.BaseTokenInfo
import com.nexters.pimo.login.domain.TokenInfo
import reactor.core.publisher.Mono

/**
 * @author yoonho
 * @since 2023.01.26
 */
interface JwtTokenUseCase {
    fun createToken(state: String, code: String): Mono<TokenInfo>
    fun authToken(input: BaseTokenInfo): Mono<String>
}