package com.nexters.pimo.follow.application.port.`in`

import reactor.core.publisher.Mono

/**
 * @author yoonho
 * @since 2023.02.16
 */
interface RegisterUseCase {
    fun register(followeeUserId: String, followerUserId: String): Mono<Boolean>
}