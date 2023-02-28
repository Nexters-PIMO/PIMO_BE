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
import com.nexters.pimo.feed.domain.Content
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class FeedService (
    private val savePort: SavePort,
    private val findPort: FindPort,
    private val deletePort: DeletePort
): SaveUseCase, FindUseCase, DeleteUseCase {
    override fun deleteById(feedId: Long): Mono<Boolean> {
        return this.deletePort.deleteById(feedId)
    }

    override fun findById(feedId: Long): Mono<FeedDto> {
        return this.findPort.findById(feedId)
    }

    override fun findByUserId(userId: String): Flux<FeedDto> {
        return this.findPort.findByUserId(userId)
    }

    override fun save(input: FeedInput): Mono<FeedDto> {
        return this.savePort.save(input)
    }

    override fun update(feedId: Long, contents: List<ContentInput>): Mono<FeedDto> {
        return this.savePort.update(feedId, contents)
    }

    override fun clap(feedId: Long, userId: String): Mono<Boolean> {
        return this.savePort.clap(feedId, userId)
    }

    override fun report(feedId: Long, userId: String): Mono<Boolean> {
        return this.savePort.report(feedId, userId)
    }
}