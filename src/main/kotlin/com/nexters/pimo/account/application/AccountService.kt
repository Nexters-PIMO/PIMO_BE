package com.nexters.pimo.account.application

import com.nexters.pimo.account.application.dto.AccountDto
import com.nexters.pimo.account.application.port.`in`.DeleteUserUseCase
import com.nexters.pimo.account.application.port.`in`.FindUserUseCase
import com.nexters.pimo.account.application.port.`in`.SaveUserUseCase
import com.nexters.pimo.account.application.port.out.DeletePort
import com.nexters.pimo.account.application.port.out.FindPort
import com.nexters.pimo.account.application.port.out.SavePort
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 * @author yoonho
 * @since 2023.02.14
 */
@Service
class AccountService(
    private val findPort: FindPort,
    private val savePort: SavePort,
    private val deletePort: DeletePort
): SaveUserUseCase, FindUserUseCase, DeleteUserUseCase {

    override fun saveUser(userId: String, nickName: String, profileImgUrl: String): Mono<AccountDto> =
        savePort.saveUser(userId, nickName, profileImgUrl).map { it.toDto() }

    override fun findUser(userId: String): Mono<AccountDto> =
        findPort.findUser(userId).map { it.toDto() }

    override fun searchUser(nickName: String): Flux<AccountDto> =
        findPort.searchUser(nickName).map { it.toDto() }

    override fun updateNickname(userId: String, nickName: String): Mono<AccountDto> =
        savePort.updateNickName(userId, nickName).map { it.toDto() }

    override fun updateProfileImgUrl(userId: String, profileImgUrl: String): Mono<AccountDto> =
        savePort.updateProfileImgUrl(userId, profileImgUrl).map { it.toDto() }

    override fun deleteUser(userId: String): Mono<Boolean> =
        deletePort.deleteUser(userId)

    override fun existsNickname(nickName: String): Mono<Boolean> =
        findPort.existsNickname(nickName)

    override fun existsUser(userId: String): Mono<Boolean> =
        findPort.existsUser(userId)
}