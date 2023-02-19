package com.nexters.pimo.follow.adapter.`in`.web

import com.nexters.pimo.common.dto.BaseResponse
import com.nexters.pimo.follow.adapter.`in`.web.dto.RegisterInput
import com.nexters.pimo.follow.application.dto.FollowAccountDto
import com.nexters.pimo.follow.application.dto.FollowCntDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.models.examples.Example
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
 * @since 2023.02.15
 */
@Configuration
class FollowRouter(
    private val followHandler: FollowHandler
) {
    @Bean
    @RouterOperations(
        value = [
            RouterOperation(
                path = "/follows",
                method = [RequestMethod.POST],
                beanClass = FollowHandler::class,
                beanMethod = "register",
                operation = Operation(
                    tags = ["친구관리"],
                    summary = "친구 등록",
                    operationId = "register",
                    requestBody = RequestBody(
                        content = [Content(schema = Schema(implementation = RegisterInput::class, required = true))]
                    ),
                    responses = [
                        // 공통 Response(BaseResponse)객체를 노출하기 위해 사용, responseCode는 swagger에서 맨 앞단에 노출시키기 위해 "0" 고정
                        ApiResponse(
                            responseCode = "0",
                            description = "공통 Response",
                            content = [Content(schema = Schema(implementation = BaseResponse::class))]
                        ),
                        ApiResponse(
                            description = "친구등록 성공여부",
                            responseCode = "200",
                            content = [Content(schema = Schema(implementation = Boolean::class))]
                        )
                    ],
                    security = [SecurityRequirement(name = "Bearer Authentication")]
                )
            ),
            RouterOperation(
                path = "/follows",
                method = [RequestMethod.DELETE],
                beanClass = FollowHandler::class,
                beanMethod = "delete",
                operation = Operation(
                    tags = ["친구관리"],
                    summary = "친구 삭제",
                    operationId = "delete",
                    requestBody = RequestBody(
                        content = [Content(schema = Schema(implementation = RegisterInput::class, required = true))]
                    ),
                    responses = [
                        // 공통 Response(BaseResponse)객체를 노출하기 위해 사용, responseCode는 swagger에서 맨 앞단에 노출시키기 위해 "0" 고정
                        ApiResponse(
                            responseCode = "0",
                            description = "공통 Response",
                            content = [Content(schema = Schema(implementation = BaseResponse::class))]
                        ),
                        ApiResponse(
                            description = "친구삭제 성공여부",
                            responseCode = "200",
                            content = [Content(schema = Schema(implementation = Boolean::class))]
                        )
                    ],
                    security = [SecurityRequirement(name = "Bearer Authentication")]
                )
            ),
            RouterOperation(
                path = "/follows/count",
                method = [RequestMethod.GET],
                beanClass = FollowHandler::class,
                beanMethod = "count",
                operation = Operation(
                    tags = ["친구관리"],
                    summary = "친구 목록수 조회",
                    operationId = "count",
                    responses = [
                        // 공통 Response(BaseResponse)객체를 노출하기 위해 사용, responseCode는 swagger에서 맨 앞단에 노출시키기 위해 "0" 고정
                        ApiResponse(
                            responseCode = "0",
                            description = "공통 Response",
                            content = [Content(schema = Schema(implementation = BaseResponse::class))]
                        ),
                        ApiResponse(
                            description = "친구 목록수",
                            responseCode = "200",
                            content = [Content(schema = Schema(implementation = FollowCntDto::class))]
                        )
                    ],
                    security = [SecurityRequirement(name = "Bearer Authentication")]
                )
            ),
            RouterOperation(
                path = "/follows/follower",
                method = [RequestMethod.GET],
                beanClass = FollowHandler::class,
                beanMethod = "list",
                operation = Operation(
                    tags = ["친구관리"],
                    summary = "나만 친구 목록조회",
                    operationId = "list",
                    parameters = [
                        Parameter(
                            name = "sort",
                            description = "정렬 방식 구분",
                            examples = [
                                ExampleObject(name = "0", value = "0", description = "친구 등록한 순서 정렬"),
                                ExampleObject(name = "1", value = "1", description = "닉네임 가나다순 정렬"),
                            ]
                        ),
                        Parameter(name = "start", description = "페이징 시작Index", example = "0"),
                        Parameter(name = "size", description = "페이징 조회할 갯수", example = "10")
                    ],
                    responses = [
                        // 공통 Response(BaseResponse)객체를 노출하기 위해 사용, responseCode는 swagger에서 맨 앞단에 노출시키기 위해 "0" 고정
                        ApiResponse(
                            responseCode = "0",
                            description = "공통 Response",
                            content = [Content(schema = Schema(implementation = BaseResponse::class))]
                        ),
                        ApiResponse(
                            description = "나만 친구 목록",
                            responseCode = "200",
                            content = [Content(schema = Schema(implementation = FollowAccountDto::class))]
                        )
                    ],
                    security = [SecurityRequirement(name = "Bearer Authentication")]
                )
            ),
            RouterOperation(
                path = "/follows/followee",
                method = [RequestMethod.GET],
                beanClass = FollowHandler::class,
                beanMethod = "list",
                operation = Operation(
                    tags = ["친구관리"],
                    summary = "상대방만 친구 목록조회",
                    operationId = "list",
                    parameters = [
                        Parameter(
                            name = "sort",
                            description = "정렬 방식 구분",
                            examples = [
                                ExampleObject(name = "0", value = "0", description = "친구 등록한 순서 정렬"),
                                ExampleObject(name = "1", value = "1", description = "닉네임 가나다순 정렬"),
                            ]
                        ),
                        Parameter(name = "start", description = "페이징 시작Index", example = "0"),
                        Parameter(name = "size", description = "페이징 조회할 갯수", example = "10")
                    ],
                    responses = [
                        // 공통 Response(BaseResponse)객체를 노출하기 위해 사용, responseCode는 swagger에서 맨 앞단에 노출시키기 위해 "0" 고정
                        ApiResponse(
                            responseCode = "0",
                            description = "공통 Response",
                            content = [Content(schema = Schema(implementation = BaseResponse::class))]
                        ),
                        ApiResponse(
                            description = "상대방만 친구 목록",
                            responseCode = "200",
                            content = [Content(schema = Schema(implementation = FollowAccountDto::class))]
                        )
                    ],
                    security = [SecurityRequirement(name = "Bearer Authentication")]
                )
            ),
            RouterOperation(
                path = "/follows/followforfollow",
                method = [RequestMethod.GET],
                beanClass = FollowHandler::class,
                beanMethod = "list",
                operation = Operation(
                    tags = ["친구관리"],
                    summary = "서로 친구 목록조회",
                    operationId = "list",
                    parameters = [
                        Parameter(
                            name = "sort",
                            description = "정렬 방식 구분",
                            examples = [
                                ExampleObject(name = "0", value = "0", description = "친구 등록한 순서 정렬"),
                                ExampleObject(name = "1", value = "1", description = "닉네임 가나다순 정렬"),
                            ]
                        ),
                        Parameter(name = "start", description = "페이징 시작Index", example = "0"),
                        Parameter(name = "size", description = "페이징 조회할 갯수", example = "10")
                     ],
                    responses = [
                        // 공통 Response(BaseResponse)객체를 노출하기 위해 사용, responseCode는 swagger에서 맨 앞단에 노출시키기 위해 "0" 고정
                        ApiResponse(
                            responseCode = "0",
                            description = "공통 Response",
                            content = [Content(schema = Schema(implementation = BaseResponse::class))]
                        ),
                        ApiResponse(
                            description = "서로 친구 목록",
                            responseCode = "200",
                            content = [Content(schema = Schema(implementation = FollowAccountDto::class))]
                        )
                    ],
                    security = [SecurityRequirement(name = "Bearer Authentication")]
                )
            )
        ]
    )
    fun followRouterFunction(): RouterFunction<ServerResponse> =
        router {
            accept(MediaType.APPLICATION_JSON).nest {
                POST("/follows", followHandler::register)
                DELETE("/follows", followHandler::delete)
                GET("/follows/count", followHandler::count)
                GET("/follows/{target}", followHandler::list)
            }
        }
}