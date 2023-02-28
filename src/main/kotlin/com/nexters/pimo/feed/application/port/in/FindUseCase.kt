package com.nexters.pimo.feed.application.port.`in`

import com.nexters.pimo.feed.application.dto.FeedDto
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface FindUseCase {
    fun findById(feedId: Long): Mono<FeedDto>
    fun findByUserId(userId: String): Flux<FeedDto>
}