package com.nexters.pimo.login.adapter.`in`.web

import com.nexters.pimo.common.dto.BaseResponse
import com.nexters.pimo.common.exception.NotFoundDataException
import com.nexters.pimo.login.application.port.`in`.JwtTokenUseCase
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

/**
 * @author yoonho
 * @since 2023.01.24
 */
@Component
class LoginHandler(
    private val jwtTokenUseCase: JwtTokenUseCase
) {
    private val log = LoggerFactory.getLogger(this::class.java)

    fun loginPage(request: ServerRequest): Mono<ServerResponse> =
        ServerResponse.ok().contentType(MediaType.TEXT_HTML).render("login")

    fun token(request: ServerRequest): Mono<ServerResponse> {
        val state = request.queryParam("state").orElseThrow { throw NotFoundDataException() }
        val code = request.queryParam("code").orElseThrow { throw NotFoundDataException() }

        return jwtTokenUseCase.createToken(state, code)
            .flatMap { BaseResponse().success(it) }
    }
}