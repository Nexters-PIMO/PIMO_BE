package com.nexters.pimo.feed.adapter.out.persistence

import com.nexters.pimo.feed.adapter.`in`.web.dto.ContentInput
import com.nexters.pimo.feed.adapter.`in`.web.dto.FeedInput
import com.nexters.pimo.feed.application.dto.FeedDto
import com.nexters.pimo.feed.application.port.out.DeletePort
import com.nexters.pimo.feed.application.port.out.FindPort
import com.nexters.pimo.feed.application.port.out.SavePort
import com.nexters.pimo.feed.domain.Content
import com.nexters.pimo.feed.domain.Feed
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
class FeedPersistenceAdapter(
    private val feedRepository: FeedRepository,
): SavePort, FindPort, DeletePort {
    override fun deleteById(feedId: Long): Mono<Boolean> {
        TODO("Not yet implemented")
    }

    override fun findById(feedId: Long): Mono<FeedDto> {
        return this.feedRepository.findByIdAndStatus(feedId,"0")
            .map { it.toDto() }
    }

    override fun findByUserId(userId: String): Flux<FeedDto> {
        return this.feedRepository.findAllByUserIdAndStatus(userId,"0")
            .map { it.toDto() }
    }

    override fun save(input: FeedInput): Mono<FeedDto> {
        val feed = Feed(
            userId = input.userId,
            contents = input.contents
        )
        println(feed)
        println(feed.contents)
        return this.feedRepository.save(feed).flatMap{ Mono.just(it.toDto())}
    }

    override fun update(feedId: Long, contents: List<ContentInput>): Mono<FeedDto> {
//        val feed = this.feedRepository.findById(feedId)
//            .flatMap { it.updateContents(contents) }
//            .flatMap {
//                this.feedRepository.save(it)
//            }
//            .map { it.toDto() }
        TODO("Not yet implemented")
    }

    override fun clap(feedId: Long, userId: String): Mono<Boolean> {
        TODO("Not yet implemented")
    }

    override fun report(feedId: Long, userId: String): Mono<Boolean> {
        TODO("Not yet implemented")
    }
}