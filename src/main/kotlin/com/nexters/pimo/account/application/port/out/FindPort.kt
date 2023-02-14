package com.nexters.pimo.account.application.port.out

import com.nexters.pimo.account.domain.Account
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 * @author yoonho
 * @since 2023.02.14
 */
interface FindPort {
    fun findUser(userId: String): Mono<Account>
    fun searchUser(nickName: String): Flux<Account>
    fun existsNickname(nickName: String): Mono<Boolean>
    fun existsUser(userId: String): Mono<Boolean>
}