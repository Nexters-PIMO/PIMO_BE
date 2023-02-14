package com.nexters.pimo.login.adapter.`in`.web

import com.nexters.pimo.common.dto.BaseResponse
import com.nexters.pimo.common.exception.BadRequestException
import com.nexters.pimo.login.application.port.`in`.JwtTokenUseCase
import com.nexters.pimo.login.application.port.`in`.TokenEncodeUseCase
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
    private val jwtTokenUseCase: JwtTokenUseCase,
    private val tokenEncodeUseCase: TokenEncodeUseCase
) {
    private val log = LoggerFactory.getLogger(this::class.java)

    fun loginPage(request: ServerRequest): Mono<ServerResponse> =
        ServerResponse.ok().contentType(MediaType.TEXT_HTML).render("login")

    fun token(request: ServerRequest): Mono<ServerResponse> =
        jwtTokenUseCase.createToken(
            request.queryParam("state").orElseThrow { throw BadRequestException("state 정보가 누락되었습니다.") },
            request.queryParam("code").orElseThrow { throw BadRequestException("code 정보가 누락되었습니다.") })
            .flatMap { BaseResponse().success(it) }

    fun encode(request: ServerRequest): Mono<ServerResponse> =
        tokenEncodeUseCase.encode(
            request.queryParam("state").orElseThrow { throw BadRequestException("state 정보가 누락되었습니다.") },
            request.queryParam("token").orElseThrow { throw BadRequestException("token 정보가 누락되었습니다.") })
            .flatMap { BaseResponse().success(it) }

    fun decode(request: ServerRequest): Mono<ServerResponse> =
        tokenEncodeUseCase.decode(
            request.queryParam("token").orElseThrow { throw BadRequestException("token 정보가 누락되었습니다.") })
            .flatMap { BaseResponse().success(it) }
}