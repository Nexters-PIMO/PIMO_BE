package com.nexters.pimo.account.application.port.`in`

import com.nexters.pimo.account.application.dto.AccountDto
import reactor.core.publisher.Mono

/**
 * @author yoonho
 * @since 2023.02.14
 */
interface DeleteUserUseCase {
    fun deleteUser(userId: String): Mono<Boolean>
}