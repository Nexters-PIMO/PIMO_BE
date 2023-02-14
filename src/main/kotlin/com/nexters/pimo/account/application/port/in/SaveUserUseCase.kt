package com.nexters.pimo.account.application.port.`in`

import com.nexters.pimo.account.application.dto.AccountDto
import reactor.core.publisher.Mono

/**
 * @author yoonho
 * @since 2023.02.14
 */
interface SaveUserUseCase {
    fun saveUser(userId: String, nickName: String, profileImgUrl: String): Mono<AccountDto>
    fun updateNickname(userId: String, nickName: String): Mono<AccountDto>
    fun updateProfileImgUrl(userId: String, profileImgUrl: String): Mono<AccountDto>
}