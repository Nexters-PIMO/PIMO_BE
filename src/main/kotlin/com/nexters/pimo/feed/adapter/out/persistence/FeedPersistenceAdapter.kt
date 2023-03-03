package com.nexters.pimo.feed.adapter.out.persistence

import com.fasterxml.jackson.databind.ObjectMapper
import com.nexters.pimo.common.exception.BadRequestException
import com.nexters.pimo.feed.adapter.`in`.web.dto.ContentInput
import com.nexters.pimo.feed.adapter.`in`.web.dto.FeedInput
import com.nexters.pimo.feed.application.dto.FeedDto
import com.nexters.pimo.feed.application.port.out.DeletePort
import com.nexters.pimo.feed.application.port.out.FindPort
import com.nexters.pimo.feed.application.port.out.SavePort
import com.nexters.pimo.feed.domain.Clap
import com.nexters.pimo.feed.domain.Content
import com.nexters.pimo.feed.domain.Feed
import com.nexters.pimo.feed.domain.Report
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

@Repository
class FeedPersistenceAdapter(
    private val feedRepository: FeedRepository,
    private val clapRepository: ClapRepository,
    private val reportRepository: ReportRepository,
    private val contentRepository: ContentRepository,
) : SavePort, FindPort, DeletePort {
    override fun findById(feedId: Long): Mono<FeedDto> =
        feedRepository.findFeedByIdAndStatus(feedId, "0")
            .map { it.toDto() }
//            .map { objectMapper.convertValue(it.toDto(), FeedDto::class.java) }

    override fun findByUserId(userId: String): Flux<FeedDto> =
        feedRepository.findAllByUserIdAndStatus(userId, "0")
            .map { it.toDto() }

    override fun findClapByFeedId(feedId: Long): Flux<Clap> =
        clapRepository.findAllByFeedId(feedId)

    override fun findHomeByUserId(userId: String): Flux<FeedDto> =
        feedRepository.findHomeByUserId(userId)
            .map { it.toDto() }

    override fun save(input: FeedInput): Mono<FeedDto> =
        feedRepository.save(
            Feed(
                userId = input.userId,
            )
        ).map {
            val contentsForInput = input.contents.map { content ->
                Content(
                    feedId = it.id,
                    caption = content.caption,
                    url = content.url
                )
            }
            contentRepository.saveAll(Flux.fromIterable(contentsForInput)).subscribe()
            it.toDto()
        }

    override fun update(feedId: Long, contents: List<ContentInput>): Mono<FeedDto> =
        feedRepository.findById(feedId)
            .map {
                contentRepository.deleteAllByFeedId(feedId)
                val contents = contents.map { content ->
                    Content(
                        feedId = it.id,
                        caption = content.caption,
                        url = content.url
                    )
                }
                val result: List<Content> = listOf()
                contentRepository.saveAll(contents).map { content -> result.plus(content) }
                it.toDto()
            }

    override fun deleteById(feedId: Long): Mono<Boolean> =
        feedRepository.findByIdAndStatus(feedId, "0")
            .switchIfEmpty { throw BadRequestException("피드가 존재하지 않습니다.") }
            .flatMap { feedRepository.deleteById(feedId) }
            .flatMap { Mono.just(true) }

    override fun clap(feedId: Long, userId: String): Mono<Boolean> =
        feedRepository.findByIdAndStatus(feedId, "0")
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
        feedRepository.findByIdAndStatus(feedId, "0")
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