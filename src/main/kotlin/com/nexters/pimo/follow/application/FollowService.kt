package com.nexters.pimo.follow.application

import com.nexters.pimo.common.exception.BadRequestException
import com.nexters.pimo.follow.application.dto.FollowAccountDto
import com.nexters.pimo.follow.application.dto.FollowCntDto
import com.nexters.pimo.follow.application.port.`in`.DeleteUseCase
import com.nexters.pimo.follow.application.port.`in`.FindUseCase
import com.nexters.pimo.follow.application.port.`in`.RegisterUseCase
import com.nexters.pimo.follow.application.port.out.FindPort
import com.nexters.pimo.follow.application.port.out.SavePort
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 * @author yoonho
 * @since 2023.02.16
 */
@Service
class FollowService(
    private val savePort: SavePort,
    private val findPort: FindPort
): RegisterUseCase, FindUseCase, DeleteUseCase {

    override fun register(followeeUserId: String, followerUserId: String): Mono<Boolean> =
        savePort.register(followeeUserId, followerUserId)

    override fun delete(followeeUserId: String, followerUserId: String): Mono<Boolean> =
        savePort.delete(followeeUserId, followerUserId)

    override fun count(userId: String): Mono<FollowCntDto> =
        findPort.count(userId)

    override fun follower(userId: String, sort: String, start: Int, size: Int): Flux<FollowAccountDto> =
        when(sort) {
            "0" -> findPort.follower(userId, start, size)
            "1" -> findPort.followerByNickname(userId, start, size)
            else -> throw BadRequestException("유효하지 않은 정렬옵션입니다")
        }

    override fun followee(userId: String, sort: String, start: Int, size: Int): Flux<FollowAccountDto> =
        when(sort) {
            "0" -> findPort.followee(userId, start, size)
            "1" -> findPort.followeeByNickname(userId, start, size)
            else -> throw BadRequestException("유효하지 않은 정렬옵션입니다")
        }

    override fun followForFollow(userId: String, sort: String, start: Int, size: Int): Flux<FollowAccountDto> =
        when(sort) {
            "0" -> findPort.followForFollow(userId, start, size)
            "1" -> findPort.followForFollowByNickname(userId, start, size)
            else -> throw BadRequestException("유효하지 않은 정렬옵션입니다")
        }
}