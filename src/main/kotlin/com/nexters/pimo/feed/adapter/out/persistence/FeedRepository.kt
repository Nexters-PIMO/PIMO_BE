package com.nexters.pimo.feed.adapter.out.persistence

import com.nexters.pimo.feed.domain.Feed
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono

interface FeedRepository : ReactiveCrudRepository<Feed, Long>, CustomFeedRepository {
    fun deleteByIdAndStatus(id: Long, status: String): Mono<Boolean>
}