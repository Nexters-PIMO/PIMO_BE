package com.nexters.pimo.feed.adapter.out.persistence

import com.nexters.pimo.feed.domain.Content
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux

interface ContentRepository : ReactiveCrudRepository<Content, Long> {
    fun findAllByFeedId(feedId: Long): Flux<Content>
    fun deleteAllByFeedId(feedId: Long): Flux<Boolean>
}