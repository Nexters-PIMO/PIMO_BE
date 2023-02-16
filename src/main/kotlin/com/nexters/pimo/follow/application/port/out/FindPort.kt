package com.nexters.pimo.follow.application.port.out

import com.nexters.pimo.follow.application.dto.FollowAccountDto
import com.nexters.pimo.follow.application.dto.FollowCntDto
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 * @author yoonho
 * @since 2023.02.16
 */
interface FindPort {
    fun count(userId: String): Mono<FollowCntDto>
    fun follower(userId: String, start: Int, size: Int): Flux<FollowAccountDto>
    fun followee(userId: String, start: Int, size: Int): Flux<FollowAccountDto>
    fun followForFollow(userId: String, start: Int, size: Int): Flux<FollowAccountDto>

    fun followerByNickname(userId: String, start: Int, size: Int): Flux<FollowAccountDto>
    fun followeeByNickname(userId: String, start: Int, size: Int): Flux<FollowAccountDto>
    fun followForFollowByNickname(userId: String, start: Int, size: Int): Flux<FollowAccountDto>
}