package com.nexters.pimo.follow.adapter.out.persistence

import com.nexters.pimo.follow.domain.Follow
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 * @author yoonho
 * @since 2023.02.16
 */
interface FollowRepository: ReactiveCrudRepository<Follow, Long> {
    fun deleteByFollowerUserIdAndFolloweeUserId(followerUserId: String, followeeUserId: String): Mono<Boolean>

    fun findAllByFollowerUserId(followerUserId: String): Flux<Follow>
    fun findAllByFolloweeUserId(followeeUserId: String): Flux<Follow>
    fun existsByFollowerUserIdAndFolloweeUserId(followerUserId: String, followeeUserId: String): Mono<Boolean>

    fun findAllByFollowerUserIdOrderByCreatedAt(followerUserId: String, pageable: Pageable): Flux<Follow>
    fun findAllByFolloweeUserIdOrderByCreatedAt(followeeUserId: String, pageable: Pageable): Flux<Follow>
    fun findAllByFollowerUserIdOrderByFollowerNickName(followerUserId: String, pageable: Pageable): Flux<Follow>
    fun findAllByFolloweeUserIdOrderByFolloweeNickName(followeeUserId: String, pageable: Pageable): Flux<Follow>
}