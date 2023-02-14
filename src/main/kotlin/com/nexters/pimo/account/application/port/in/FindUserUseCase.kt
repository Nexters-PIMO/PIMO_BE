package com.nexters.pimo.account.application.port.`in`

import com.nexters.pimo.account.application.dto.AccountDto
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 * @author yoonho
 * @since 2023.02.14
 */
interface FindUserUseCase {
    fun existsUser(userId: String): Mono<Boolean>
    fun findUser(userId: String): Mono<AccountDto>
    fun searchUser(nickName: String): Flux<AccountDto>
    fun existsNickname(nickName: String): Mono<Boolean>
}