package com.nexters.pimo.feed.application.port.out

import com.nexters.pimo.feed.adapter.`in`.web.dto.ContentInput
import com.nexters.pimo.feed.adapter.`in`.web.dto.FeedInput
import com.nexters.pimo.feed.application.dto.FeedDto
import reactor.core.publisher.Mono

interface SavePort {
    fun save(input: FeedInput): Mono<FeedDto>
    fun update(feedId: Long, contents: List<ContentInput>): Mono<FeedDto>
    fun clap(feedId: Long, userId: String): Mono<Boolean>
    fun report(feedId: Long, userId: String): Mono<Boolean>
}