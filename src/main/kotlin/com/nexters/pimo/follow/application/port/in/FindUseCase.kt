package com.nexters.pimo.follow.application.port.`in`

import com.nexters.pimo.follow.application.dto.FollowAccountDto
import com.nexters.pimo.follow.application.dto.FollowCntDto
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 * @author yoonho
 * @since 2023.02.16
 */
interface FindUseCase {
    fun count(userId: String): Mono<FollowCntDto>
    fun follower(userId: String, sort: String, start: Int, size: Int): Flux<FollowAccountDto>
    fun followee(userId: String, sort: String, start: Int, size: Int): Flux<FollowAccountDto>
    fun followForFollow(userId: String, sort: String, start: Int, size: Int): Flux<FollowAccountDto>
}