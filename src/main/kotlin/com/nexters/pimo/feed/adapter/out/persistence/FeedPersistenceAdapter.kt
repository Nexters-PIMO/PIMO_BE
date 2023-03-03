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
import reactor.kotlin.core.publisher.toMono

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
        feedRepository.saveFeed(input.userId)
            .flatMap {
                println(it)
                contentRepository.saveAll(Flux.fromIterable(input.contents.map { content ->
                    Content(
                        feedId = it.id,
                        caption = content.caption,
                        url = content.url
                    )
                })).then()
            }.flatMap { Mono.just(true) }
        //        findById(1, "admin1")
//            .flatMap {
//                println(it)
//                Mono.just(true)
//            }
//        feedRepository.save(
//            Feed(
//                userId = input.userId,
//            )
//        )
//            .flatMap {
//            println(it)
//            Mono.just(true)
//////            val contentsForInput = input.contents.map { content ->
//////                Content(
//////                    feedId = it.id,
//////                    caption = content.caption,
//////                    url = content.url
//////                )
//////            }
////            println(it)
////            contentRepository.saveAll(input.contents.map { content ->
////                Content(
////                    feedId = it.id,
////                    caption = content.caption,
////                    url = content.url
////                )
////            }).last().flatMap{ Mono.just(true) }
//        }

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
            .flatMap { feedRepository.deleteById(feedId) }
            .flatMap { Mono.just(true) }

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