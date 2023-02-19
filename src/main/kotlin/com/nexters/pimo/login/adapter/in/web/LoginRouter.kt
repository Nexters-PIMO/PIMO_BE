package com.nexters.pimo.login.adapter.`in`.web

import com.nexters.pimo.common.dto.BaseResponse
import com.nexters.pimo.common.dto.BaseTokenInfo
import com.nexters.pimo.follow.adapter.`in`.web.dto.RegisterInput
import com.nexters.pimo.follow.application.dto.FollowAccountDto
import com.nexters.pimo.follow.application.dto.FollowCntDto
import com.nexters.pimo.login.domain.TokenInfo
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springdoc.core.annotations.RouterOperation
import org.springdoc.core.annotations.RouterOperations
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router

/**
 * @author yoonho
 * @since 2023.01.24
 */
@Configuration
class LoginRouter(
    private val loginHandler: LoginHandler
) {

    @Bean
    @RouterOperations(
        value = [
            RouterOperation(
                path = "/login/token",
                method = [RequestMethod.GET],
                beanClass = LoginHandler::class,
                beanMethod = "token",
                operation = Operation(
                    tags = ["로그인관리"],
                    summary = "토큰정보 조회",
                    operationId = "token",
                    parameters = [
                        Parameter(
                            name = "state",
                            description = "소셜로그인 구분 구분",
                            examples = [
                                ExampleObject(name = "kakao", value = "kakao", description = "카카오 로그인"),
                                ExampleObject(name = "apple", value = "apple", description = "애플 로그인"),
                            ]
                        ),
                        Parameter(name = "code", description = "인가코드(identity_token)", example = "0"),
                    ],
                    responses = [
                        // 공통 Response(BaseResponse)객체를 노출하기 위해 사용, responseCode는 swagger에서 맨 앞단에 노출시키기 위해 "0" 고정
                        ApiResponse(
                            responseCode = "0",
                            description = "공통 Response",
                            content = [Content(schema = Schema(implementation = BaseResponse::class))]
                        ),
                        ApiResponse(
                            description = "획득한 토큰정보",
                            responseCode = "200",
                            content = [Content(schema = Schema(implementation = TokenInfo::class))]
                        )
                    ],
                )
            ),
            RouterOperation(
                path = "/login/encode",
                method = [RequestMethod.GET],
                beanClass = LoginHandler::class,
                beanMethod = "encode",
                operation = Operation(
                    tags = ["로그인관리"],
                    summary = "토큰 인코딩",
                    operationId = "encode",
                    parameters = [Parameter(name = "token", description = "토큰", example = "0")],
                    responses = [
                        // 공통 Response(BaseResponse)객체를 노출하기 위해 사용, responseCode는 swagger에서 맨 앞단에 노출시키기 위해 "0" 고정
                        ApiResponse(
                            responseCode = "0",
                            description = "공통 Response",
                            content = [Content(schema = Schema(implementation = BaseResponse::class))]
                        ),
                        ApiResponse(
                            description = "인코딩한 토큰정보",
                            responseCode = "200",
                            content = [Content(schema = Schema(implementation = BaseTokenInfo::class))]
                        )
                    ],
                )
            ),
        ]
    )
    fun loginRouterFunction(): RouterFunction<ServerResponse> =
        router {
            accept(MediaType.APPLICATION_JSON).nest {
                GET("/login", loginHandler::loginPage)
                GET("/login/token", loginHandler::token)
                GET("/login/encode", loginHandler::encode)
                GET("/login/decode", loginHandler::decode)
            }
        }
}