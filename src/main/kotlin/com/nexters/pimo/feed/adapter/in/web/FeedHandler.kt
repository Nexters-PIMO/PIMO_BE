package com.nexters.pimo.feed.adapter.`in`.web

import com.nexters.pimo.common.dto.BaseResponse
import com.nexters.pimo.common.filter.userId
import com.nexters.pimo.feed.adapter.`in`.web.dto.ContentInput
import com.nexters.pimo.feed.adapter.`in`.web.dto.FeedInput
import com.nexters.pimo.feed.application.port.`in`.DeleteUseCase
import com.nexters.pimo.feed.application.port.`in`.FindUseCase
import com.nexters.pimo.feed.application.port.`in`.SaveUseCase
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class FeedHandler(
    private val saveUseCase: SaveUseCase,
    private val findUseCase: FindUseCase,
    private val deleteUseCase: DeleteUseCase
) {

    fun findById(request: ServerRequest): Mono<ServerResponse> =
        findUseCase.findById(
            request.pathVariable("feedId").toLong(),
            request.userId()
        ).flatMap {
            BaseResponse().success(it)
        }

    fun findByUserId(request: ServerRequest): Mono<ServerResponse> =
        findUseCase.findByUserId(request.userId()).collectList()
            .flatMap {
                BaseResponse().success(it)
            }

    fun save(request: ServerRequest): Mono<ServerResponse> =
        request.bodyToMono(FeedInput::class.java)
            .flatMap { saveUseCase.save(it) }
            .flatMap { BaseResponse().success(it) }

    fun update(request: ServerRequest): Mono<ServerResponse> =
        request.bodyToFlux(ContentInput::class.java)
            .collectList()
            .flatMap { saveUseCase.update(request.pathVariable("feedId").toLong(), it, request.userId()) }
            .flatMap { BaseResponse().success(it) }

    fun home(request: ServerRequest): Mono<ServerResponse> =
        findUseCase.findHomeByUserId(request.pathVariable("userId"))
            .collectList()
            .flatMap { BaseResponse().success(it) }

    fun delete(request: ServerRequest): Mono<ServerResponse> =
        deleteUseCase.deleteById(request.pathVariable("feedId").toLong())
            .flatMap { BaseResponse().success(true) }


    fun clap(request: ServerRequest): Mono<ServerResponse> =
        saveUseCase.clap(request.pathVariable("feedId").toLong(), request.pathVariable("userId"))
            .flatMap { BaseResponse().success(true) }

    fun report(request: ServerRequest): Mono<ServerResponse> =
        saveUseCase.report(request.pathVariable("feedId").toLong(), request.pathVariable("userId"))
            .flatMap { BaseResponse().success(true) }

}
