package com.nexters.pimo.account.application.port.out

import reactor.core.publisher.Mono

/**
 * @author yoonho
 * @since 2023.02.14
 */
interface DeletePort {
    fun deleteUser(userId: String): Mono<Boolean>
}