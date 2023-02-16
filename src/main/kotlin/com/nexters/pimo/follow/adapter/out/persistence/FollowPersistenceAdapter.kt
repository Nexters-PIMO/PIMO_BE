package com.nexters.pimo.follow.adapter.out.persistence

import com.fasterxml.jackson.databind.ObjectMapper
import com.nexters.pimo.account.adapter.out.persistence.AccountRepository
import com.nexters.pimo.common.exception.BadRequestException
import com.nexters.pimo.follow.application.dto.FollowAccountDto
import com.nexters.pimo.follow.application.dto.FollowCntDto
import com.nexters.pimo.follow.application.port.out.FindPort
import com.nexters.pimo.follow.application.port.out.SavePort
import com.nexters.pimo.follow.domain.Follow
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import java.util.*
import java.util.stream.Collectors

/**
 * @author yoonho
 * @since 2023.02.15
 */
@Repository
class FollowPersistenceAdapter(
    private val followRepository: FollowRepository,
    private val accountRepository: AccountRepository,
    private val objectMapper: ObjectMapper,
): SavePort, FindPort {

    override fun register(followeeUserId: String, followerUserId: String): Mono<Boolean> =
        Mono.zip(
            accountRepository.findByUserId(followeeUserId)
                .switchIfEmpty { throw BadRequestException("등록되지 않은 사용자입니다.") },
            accountRepository.findByUserId(followerUserId)
                .switchIfEmpty { throw BadRequestException("등록되지 않은 사용자입니다.") }
        )
        .flatMap { followRepository.save(
            Follow(
                followeeUserId = followeeUserId,
                followeeNickName = it.t1.nickName,
                followerUserId = followerUserId,
                followerNickName = it.t2.nickName
            )
        )
        .flatMap { Mono.just(true) } }

    override fun delete(followeeUserId: String, followerUserId: String): Mono<Boolean> =
        followRepository.deleteByFollowerUserIdAndFolloweeUserId(followerUserId, followeeUserId)

    override fun count(userId: String): Mono<FollowCntDto> =
        Mono.zip(
            followRepository.findAllByFollowerUserId(userId)
                .filterWhen {
                    followRepository.existsByFollowerUserIdAndFolloweeUserId(it.followeeUserId, userId)
                        .map { !it }    // not exists 조건
                }
                .count(),
            followRepository.findAllByFolloweeUserId(userId)
                .filterWhen {
                    followRepository.existsByFollowerUserIdAndFolloweeUserId(userId, it.followerUserId)
                        .map { !it }    // not exists 조건
                }
                .count(),
            followRepository.findAllByFollowerUserId(userId)
                .filterWhen {
                    followRepository.existsByFollowerUserIdAndFolloweeUserId(it.followeeUserId, userId)
                }
                .count()
        )
        .map {
            FollowCntDto(
                followerCnt = it.t1,
                followeeCnt = it.t2,
                followForFollowCnt = it.t3
            )
        }

    override fun follower(userId: String, start: Int, size: Int): Flux<FollowAccountDto> =
        followRepository.findAllByFollowerUserIdOrderByCreatedAt(userId, PageRequest.of(start, size))
            .filterWhen {
                followRepository.existsByFollowerUserIdAndFolloweeUserId(it.followeeUserId, userId)
                    .map { !it }    // not exists 조건
            }
            .flatMap { findFollowAccountDto(it.followeeUserId) }

    override fun followee(userId: String, start: Int, size: Int): Flux<FollowAccountDto> =
        followRepository.findAllByFolloweeUserIdOrderByCreatedAt(userId, PageRequest.of(start, size))
            .filterWhen {
                followRepository.existsByFollowerUserIdAndFolloweeUserId(userId, it.followerUserId)
                    .map { !it }    // not exists 조건
            }
            .flatMap { findFollowAccountDto(it.followerUserId) }

    override fun followForFollow(userId: String, start: Int, size: Int): Flux<FollowAccountDto>  =
        followRepository.findAllByFollowerUserIdOrderByCreatedAt(userId, PageRequest.of(start, size))
            .filterWhen {
                followRepository.existsByFollowerUserIdAndFolloweeUserId(it.followeeUserId, userId)
            }
            .flatMap { findFollowAccountDto(it.followeeUserId) }

    override fun followerByNickname(userId: String, start: Int, size: Int): Flux<FollowAccountDto> =
        followRepository.findAllByFollowerUserIdOrderByFollowerNickName(userId, PageRequest.of(start, size))
            .filterWhen {
                followRepository.existsByFollowerUserIdAndFolloweeUserId(it.followeeUserId, userId)
                    .map { !it }    // not exists 조건
            }
            .flatMap { findFollowAccountDto(it.followeeUserId) }

    override fun followeeByNickname(userId: String, start: Int, size: Int): Flux<FollowAccountDto> =
        followRepository.findAllByFolloweeUserIdOrderByFolloweeNickName(userId, PageRequest.of(start, size))
            .filterWhen {
                followRepository.existsByFollowerUserIdAndFolloweeUserId(userId, it.followerUserId)
                    .map { !it }    // not exists 조건
            }
            .flatMap { findFollowAccountDto(it.followerUserId) }

    override fun followForFollowByNickname(userId: String, start: Int, size: Int): Flux<FollowAccountDto> =
        followRepository.findAllByFollowerUserIdOrderByFollowerNickName(userId, PageRequest.of(start, size))
            .filterWhen {
                followRepository.existsByFollowerUserIdAndFolloweeUserId(it.followeeUserId, userId)
            }
            .flatMap { findFollowAccountDto(it.followeeUserId) }


    private fun findFollowAccountDto(userId: String): Mono<FollowAccountDto> =
        accountRepository.findByUserId(userId)
            .switchIfEmpty { throw BadRequestException("등록되지 않은 사용자입니다.") }
            .map { it.toDto() }
            .map { objectMapper.convertValue(it, FollowAccountDto::class.java) }


}