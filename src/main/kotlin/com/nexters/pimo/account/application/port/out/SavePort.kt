package com.nexters.pimo.account.application.port.out

import com.nexters.pimo.account.domain.Account
import reactor.core.publisher.Mono

/**
 * @author yoonho
 * @since 2023.02.14
 */
interface SavePort {
    fun saveUser(userId: String, nickName: String, profileImgUrl: String): Mono<Account>
    fun updateNickName(userId: String, nickName: String): Mono<Account>
    fun updateProfileImgUrl(userId: String, profileImgUrl: String): Mono<Account>
}