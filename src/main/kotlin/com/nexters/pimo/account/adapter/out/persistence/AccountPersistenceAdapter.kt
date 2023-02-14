package com.nexters.pimo.account.adapter.out.persistence

import com.nexters.pimo.account.application.port.out.DeletePort
import com.nexters.pimo.account.application.port.out.FindPort
import com.nexters.pimo.account.application.port.out.SavePort
import com.nexters.pimo.account.domain.Account
import com.nexters.pimo.common.exception.BadRequestException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import java.time.LocalDateTime

/**
 * @author yoonho
 * @since 2023.02.15
 */
@Repository
class AccountPersistenceAdapter(
    private val accountRepository: AccountRepository
): FindPort, SavePort, DeletePort {
    private val log = LoggerFactory.getLogger(this::class.java)

    override fun saveUser(userId: String, nickName: String, profileImgUrl: String): Mono<Account> =
        accountRepository.findByUserId(userId)
            .switchIfEmpty {
                accountRepository.save(
                    Account(
                        userId = userId,
                        nickName = nickName,
                        profileImgUrl = profileImgUrl,
                        status = "0",
                        updatedAt = LocalDateTime.now()
                    )
                )
            }

    override fun findUser(userId: String): Mono<Account> =
        accountRepository.findByUserId(userId)
            .switchIfEmpty { throw BadRequestException("등록되지 않은 사용자입니다.") }

    override fun searchUser(nickName: String): Flux<Account> =
        accountRepository.findByNickNameContaining(nickName)

    override fun updateNickName(userId: String, nickName: String): Mono<Account> =
        accountRepository.findByUserId(userId)
            .switchIfEmpty { throw BadRequestException("등록되지 않은 사용자입니다.") }
            .map { it.changeNickName(nickName) }
            .flatMap { accountRepository.save(it) }

    override fun updateProfileImgUrl(userId: String, profileImgUrl: String): Mono<Account> =
        accountRepository.findByUserId(userId)
            .switchIfEmpty { throw BadRequestException("등록되지 않은 사용자입니다.") }
            .map { it.changeProfileImgUrl(profileImgUrl) }
            .flatMap { accountRepository.save(it) }

    override fun deleteUser(userId: String): Mono<Boolean> =
        accountRepository.findByUserId(userId)
            .switchIfEmpty { throw BadRequestException("등록되지 않은 사용자입니다.") }
            .flatMap { accountRepository.deleteByUserId(userId) }

    override fun existsNickname(nickName: String): Mono<Boolean> =
        accountRepository.existsByNickName(nickName)

    override fun existsUser(userId: String): Mono<Boolean> =
        accountRepository.existsByUserId(userId)
}