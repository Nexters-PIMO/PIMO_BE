package com.nexters.pimo.feed.application.port.`in`

import com.nexters.pimo.feed.adapter.`in`.web.dto.ContentInput
import com.nexters.pimo.feed.adapter.`in`.web.dto.FeedInput
import com.nexters.pimo.feed.application.dto.FeedDto
import reactor.core.publisher.Mono

interface SaveUseCase {
    fun save(input: FeedInput): Mono<Boolean>
    fun update(feedId: Long, contents: List<ContentInput>, userId: String): Mono<FeedDto>
    fun clap(feedId: Long, userId: String): Mono<Boolean>
    fun report(feedId: Long, userId: String): Mono<Boolean>
}