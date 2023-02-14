package com.nexters.pimo.account.adapter.out.persistence

import com.nexters.pimo.account.domain.Account
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


/**
 * @author yoonho
 * @since 2023.02.14
 */
interface AccountRepository: ReactiveCrudRepository<Account, Long> {
    fun findByNickNameContaining(nickName: String): Flux<Account>
    fun findByUserId(userId: String): Mono<Account>
    fun deleteByUserId(userId: String): Mono<Boolean>
    fun existsByNickName(nickName: String): Mono<Boolean>
    fun existsByUserId(userId: String): Mono<Boolean>
}