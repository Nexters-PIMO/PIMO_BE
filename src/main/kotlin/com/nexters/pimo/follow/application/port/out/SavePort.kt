package com.nexters.pimo.follow.application.port.out

import reactor.core.publisher.Mono

/**
 * @author yoonho
 * @since 2023.02.16
 */
interface SavePort {
    fun register(followeeUserId: String, followerUserId: String): Mono<Boolean>
    fun delete(followeeUserId: String, followerUserId: String): Mono<Boolean>
}