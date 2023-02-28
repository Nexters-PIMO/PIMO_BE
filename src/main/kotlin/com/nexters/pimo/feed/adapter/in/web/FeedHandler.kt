package com.nexters.pimo.feed.adapter.`in`.web

import com.nexters.pimo.common.dto.BaseResponse
import com.nexters.pimo.common.exception.BadRequestException
import com.nexters.pimo.common.filter.userId
import com.nexters.pimo.feed.adapter.`in`.web.dto.ContentInput
import com.nexters.pimo.feed.adapter.`in`.web.dto.FeedInput
import com.nexters.pimo.feed.application.port.`in`.DeleteUseCase
import com.nexters.pimo.feed.application.port.`in`.FindUseCase
import com.nexters.pimo.feed.application.port.`in`.SaveUseCase
import org.springframework.core.ParameterizedTypeReference
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class FeedHandler(
    private val saveUseCase: SaveUseCase,
    private val findUseCase: FindUseCase,
    private val deleteUseCase: DeleteUseCase
) {
//    fun findById(request: ServerRequest): Mono<ServerResponse> =
//        this.findUseCase.findById(request.pathVariable("feedId").orElseThrow { throw BadRequestException("잘못된 요청입니다.") }.toLong())
//            .flatMap{BaseResponse().success(it)}

    fun findById(request: ServerRequest): Mono<ServerResponse> {
        println(request.pathVariable("feedId"))
        return this.findUseCase.findById(
            request.pathVariable("feedId").toLong()
        )
            .flatMap { BaseResponse().success(it) }
    }

    fun findByUserId(request: ServerRequest): Mono<ServerResponse> =
        this.findUseCase.findByUserId(request.userId())
            .collectList()
            .flatMap{ BaseResponse().success(it) }

    fun save(request: ServerRequest): Mono<ServerResponse> =
        request.bodyToMono(FeedInput::class.java)
            .flatMap { this.saveUseCase.save(it) }
            .flatMap { BaseResponse().success(it) }

    fun update(request: ServerRequest): Mono<ServerResponse> =
        request.bodyToFlux(ContentInput::class.java)
            .collectList()
            .flatMap {this.saveUseCase.update(request.pathVariable("feedId").toLong(), it)}
            .flatMap { BaseResponse().success(it) }

    fun home(request: ServerRequest): Mono<ServerResponse> =
        BaseResponse().success(true)

    fun delete(request: ServerRequest): Mono<ServerResponse> =
        BaseResponse().success(true)

    fun clap(request: ServerRequest): Mono<ServerResponse> =
        BaseResponse().success(true)

    fun report(request: ServerRequest): Mono<ServerResponse> =
        BaseResponse().success(true)

}
