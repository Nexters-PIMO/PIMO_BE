package com.nexters.pimo.login.application.port.`in`

import com.nexters.pimo.common.dto.BaseTokenInfo
import reactor.core.publisher.Mono

/**
 * @author yoonho
 * @since 2023.02.12
 */
interface TokenEncodeUseCase {
    fun encode(state: String, token: String): Mono<BaseTokenInfo>
    fun decode(token: String): Mono<BaseTokenInfo>
}