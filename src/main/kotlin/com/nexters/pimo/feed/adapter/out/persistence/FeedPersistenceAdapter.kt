package com.nexters.pimo.feed.adapter.out.persistence

import com.nexters.pimo.common.exception.BadRequestException
import com.nexters.pimo.feed.adapter.`in`.web.dto.ContentInput
import com.nexters.pimo.feed.adapter.`in`.web.dto.FeedInput
import com.nexters.pimo.feed.application.dto.FeedDto
import com.nexters.pimo.feed.application.port.out.DeletePort
import com.nexters.pimo.feed.application.port.out.FindPort
import com.nexters.pimo.feed.application.port.out.SavePort
import com.nexters.pimo.feed.domain.Clap
import com.nexters.pimo.feed.domain.Content
import com.nexters.pimo.feed.domain.Report
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import reactor.kotlin.core.publisher.switchIfEmpty

@Repository
class FeedPersistenceAdapter(
    private val feedRepository: FeedRepository,
    private val clapRepository: ClapRepository,
    private val reportRepository: ReportRepository,
    private val contentRepository: ContentRepository,
) : SavePort, FindPort, DeletePort {
    override fun findById(feedId: Long, userId: String): Mono<FeedDto> =
        feedRepository.findByIdWithContentAndClap(feedId)
            .map { it.toDto(userId) }

    override fun findByUserId(userId: String): Flux<FeedDto> =
        feedRepository.findAllByUserIdWithContentAndClap(userId)
            .map { it.toDto(userId) }

    override fun findHomeByUserId(userId: String): Flux<FeedDto> =
        feedRepository.findHomeByUserIdWithContentAndClap(userId)
            .map { it.toDto(userId) }

    override fun save(input: FeedInput): Mono<Boolean> =
        feedRepository.saveFeed(input.userId).map {
            input.contents.map { content ->
                Content(
//                    feedId = it["id"] as Long,
                    feedId = it,
                    caption = content.caption,
                    url = content.url
                )
            }
        }
        .publishOn(Schedulers.boundedElastic())
        .map { contentRepository.saveAll(it).subscribe() }
        .flatMap { Mono.just(true) }

    override fun update(feedId: Long, contents: List<ContentInput>, userId: String): Mono<FeedDto> =
        contentRepository.deleteAllByFeedId(feedId).flatMap {
            contentRepository.saveAll(contents.map { content ->
                Content(
                    feedId = feedId,
                    caption = content.caption,
                    url = content.url
                )
            }).last()
        }.flatMap { findById(feedId, userId) }

    override fun deleteById(feedId: Long): Mono<Boolean> =
        feedRepository.findByIdWithContentAndClap(feedId)
            .switchIfEmpty { throw BadRequestException("피드가 존재하지 않습니다.") }
            .flatMap { feedRepository.deleteByIdAndStatus(feedId, "0") }
//            .flatMap { Mono.just(true) }

    override fun clap(feedId: Long, userId: String): Mono<Boolean> =
        feedRepository.findByIdWithContentAndClap(feedId)
            .switchIfEmpty { throw BadRequestException("피드가 존재하지 않습니다.") }
            .flatMap {
                clapRepository.save(
                    Clap(
                        userId = userId,
                        feedId = feedId
                    )
                )
            }
            .flatMap { Mono.just(true) }

    override fun report(feedId: Long, userId: String): Mono<Boolean> =
        feedRepository.findByIdWithContentAndClap(feedId)
            .switchIfEmpty { throw BadRequestException("피드가 존재하지 않습니다.") }
            .flatMap {
                reportRepository.save(
                    Report(
                        userId = userId,
                        feedId = feedId
                    )
                )
            }
            .flatMap { Mono.just(true) }

}