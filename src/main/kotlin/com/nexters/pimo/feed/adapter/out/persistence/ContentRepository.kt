package com.nexters.pimo.feed.adapter.out.persistence

import com.nexters.pimo.feed.domain.Content
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono

interface ContentRepository : ReactiveCrudRepository<Content, Long> {
    fun deleteAllByFeedId(feedId: Long): Mono<Boolean>
}