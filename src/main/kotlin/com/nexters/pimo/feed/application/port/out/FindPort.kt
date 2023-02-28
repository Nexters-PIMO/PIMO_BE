package com.nexters.pimo.feed.application.port.out

import com.nexters.pimo.feed.application.dto.FeedDto
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface FindPort {
    fun findById(feedId: Long): Mono<FeedDto>
    fun findByUserId(userId: String): Flux<FeedDto>
}