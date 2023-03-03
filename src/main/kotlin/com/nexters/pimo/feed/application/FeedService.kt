package com.nexters.pimo.feed.application

import com.nexters.pimo.feed.adapter.`in`.web.dto.ContentInput
import com.nexters.pimo.feed.adapter.`in`.web.dto.FeedInput
import com.nexters.pimo.feed.application.dto.FeedDto
import com.nexters.pimo.feed.application.port.`in`.DeleteUseCase
import com.nexters.pimo.feed.application.port.`in`.FindUseCase
import com.nexters.pimo.feed.application.port.`in`.SaveUseCase
import com.nexters.pimo.feed.application.port.out.DeletePort
import com.nexters.pimo.feed.application.port.out.FindPort
import com.nexters.pimo.feed.application.port.out.SavePort
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class FeedService(
    private val savePort: SavePort,
    private val findPort: FindPort,
    private val deletePort: DeletePort
) : SaveUseCase, FindUseCase, DeleteUseCase {
    override fun deleteById(feedId: Long): Mono<Boolean> =
        deletePort.deleteById(feedId)

    override fun findById(feedId: Long, userId: String): Mono<FeedDto> =
        findPort.findById(feedId, userId)


    override fun findByUserId(userId: String): Flux<FeedDto> =
        findPort.findByUserId(userId)


    override fun findHomeByUserId(userId: String): Flux<FeedDto> =
        findPort.findHomeByUserId(userId)


    override fun save(input: FeedInput): Mono<Boolean> =
        savePort.save(input)


    override fun update(feedId: Long, contents: List<ContentInput>, userId: String): Mono<FeedDto> =
        savePort.update(feedId, contents, userId)


    override fun clap(feedId: Long, userId: String): Mono<Boolean> =
        savePort.clap(feedId, userId)


    override fun report(feedId: Long, userId: String): Mono<Boolean> =
        savePort.report(feedId, userId)

}