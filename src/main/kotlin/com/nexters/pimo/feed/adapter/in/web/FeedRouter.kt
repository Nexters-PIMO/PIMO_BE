package com.nexters.pimo.feed.adapter.`in`.web

import com.nexters.pimo.common.dto.BaseResponse
import com.nexters.pimo.feed.adapter.`in`.web.dto.ContentInput
import com.nexters.pimo.feed.adapter.`in`.web.dto.FeedInput
import com.nexters.pimo.feed.application.dto.FeedDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
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

@Configuration
class FeedRouter (
    private val feedHandler: FeedHandler
){
    @Bean
    @RouterOperations(
        value = [
            RouterOperation(
                path = "/users/{userId}/feeds",
                method = [RequestMethod.GET],
                beanClass = FeedHandler::class,
                beanMethod = "findByUserId",
                operation = Operation(
                    tags = ["피드 관리"],
                    summary = "특정 유저의 피드 목록 조회",
                    operationId = "findByUserId",
                    parameters = [
                        Parameter(
                            name = "userId",
                            `in` = ParameterIn.PATH,
                            description = "피드 목록을 조회하려는 유저의 ID"
                        ),
                        Parameter(name = "start", description = "페이징 시작 Index (반영안됨)", example = "0"),
                        Parameter(name = "size", description = "페이징 조회할 갯수 (반영안됨)", example = "10")
                     ],
                    responses = [
                        ApiResponse(
                            responseCode = "0",
                            description = "공통 Response",
                            content = [Content(schema = Schema(implementation = BaseResponse::class))]
                        ),
                        ApiResponse(
                            description = "피드 내용",
                            responseCode = "200",
                            content = [Content(schema = Schema(implementation = FeedDto::class))]
                        )
                    ],
                    security = [SecurityRequirement(name = "Bearer Authentication")]
                )
            ),
            RouterOperation(
                path = "/users/{userId}/feeds/{feedId}",
                method = [RequestMethod.GET],
                beanClass = FeedHandler::class,
                beanMethod = "findById",
                operation = Operation(
                    tags = ["피드 관리"],
                    summary = "특정 피드 조회 (1건)",
                    operationId = "findById",
                    parameters = [
                        Parameter(
                            name = "userId",
                            `in` = ParameterIn.PATH,
                            description = "피드 목록을 조회하려는 유저의 ID"
                        ),
                        Parameter(
                            name = "feedId",
                            `in` = ParameterIn.PATH,
                            description = "조회하려는 피드의 ID"
                        ),
                    ],
                    responses = [
                        ApiResponse(
                            responseCode = "0",
                            description = "공통 Response",
                            content = [Content(schema = Schema(implementation = BaseResponse::class))]
                        ),
                        ApiResponse(
                            description = "피드 내용",
                            responseCode = "200",
                            content = [Content(schema = Schema(implementation = FeedDto::class))]
                        )
                    ],
                    security = [SecurityRequirement(name = "Bearer Authentication")]
                )
            ),
            RouterOperation(
                path = "/users/{userId}/home",
                method = [RequestMethod.GET],
                beanClass = FeedHandler::class,
                beanMethod = "home",
                operation = Operation(
                    tags = ["피드 관리"],
                    summary = "피드 홈 조회",
                    operationId = "home",
                    parameters = [
                        Parameter(
                            name = "userId",
                            `in` = ParameterIn.PATH,
                            description = "홈을 조회하려는 유저의 ID"
                        ),
                        Parameter(name = "start", description = "페이징 시작 Index (반영안됨)", example = "0"),
                        Parameter(name = "size", description = "페이징 조회할 갯수 (반영안됨)", example = "10")
                    ],
                    responses = [
                        ApiResponse(
                            responseCode = "0",
                            description = "공통 Response",
                            content = [Content(schema = Schema(implementation = BaseResponse::class))]
                        ),
                        ApiResponse(
                            description = "피드 내용",
                            responseCode = "200",
                            content = [Content(schema = Schema(implementation = FeedDto::class))]
                        )
                    ],
                    security = [SecurityRequirement(name = "Bearer Authentication")]
                )

            ),
            RouterOperation(
                path = "/users/{userId}/feeds",
                method = [RequestMethod.POST],
                beanClass = FeedHandler::class,
                beanMethod = "save",
                operation = Operation(
                    tags = ["피드 관리"],
                    summary = "피드 저장",
                    operationId = "save",
                    parameters = [
                        Parameter(
                            name = "userId",
                            `in` = ParameterIn.PATH,
                            description = "피드를 저장하려는 유저의 id"
                        ),
                    ],
                    requestBody = RequestBody(
                        content = [Content(schema = Schema(implementation = FeedInput::class, required = true))]
                    ),
                    responses = [
                        ApiResponse(
                            responseCode = "0",
                            description = "공통 Response",
                            content = [Content(schema = Schema(implementation = BaseResponse::class))]
                        ),
                        ApiResponse(
                            description = "성공시 true",
                            responseCode = "200",
                            content = [Content(schema = Schema(implementation = Boolean::class))]
                        )
                    ],
                    security = [SecurityRequirement(name = "Bearer Authentication")]
                )
            ),
            RouterOperation(
                path = "/users/{userId}/feeds/{feedId}",
                method = [RequestMethod.PATCH],
                beanClass = FeedHandler::class,
                beanMethod = "update",
                operation = Operation(
                    tags = ["피드 관리"],
                    summary = "피드 수정 (이미지 및 텍스트 수정, 본인것만 수정 가능)",
                    operationId = "update",
                    parameters = [
                        Parameter(
                            name = "userId",
                            `in` = ParameterIn.PATH,
                            description = "수정하려는 피드를 소유한 유저의 ID"
                        ),
                        Parameter(
                            name = "feedId",
                            `in` = ParameterIn.PATH,
                            description = "수정하려는 피드의 ID"
                        ),
                    ],
                    requestBody = RequestBody(
                        content = [Content(array = ArraySchema(schema = Schema(implementation = ContentInput::class, required = true)))]
                    ),
                    responses = [
                        ApiResponse(
                            responseCode = "0",
                            description = "공통 Response",
                            content = [Content(schema = Schema(implementation = BaseResponse::class))]
                        ),
                        ApiResponse(
                            description = "수정된 피드 정보",
                            responseCode = "200",
                            content = [Content(schema = Schema(implementation = FeedDto::class))]
                        )
                    ],
                    security = [SecurityRequirement(name = "Bearer Authentication")]
                )
            ),
            RouterOperation(
                path = "/users/{userId}/feeds/{feedId}",
                method = [RequestMethod.DELETE],
                beanClass = FeedHandler::class,
                beanMethod = "delete",
                operation = Operation(
                    tags = ["피드 관리"],
                    summary = "피드 삭제 (본인의 피드만 삭제 가능)",
                    operationId = "delete",
                    parameters = [
                        Parameter(
                            name = "userId",
                            `in` = ParameterIn.PATH,
                            description = "피드 목록을 조회하려는 유저의 ID"
                        ),
                        Parameter(
                            name = "feedId",
                            `in` = ParameterIn.PATH,
                            description = "삭제하려는 피드의 ID"
                        ),
                    ],
                    responses = [
                        ApiResponse(
                            responseCode = "0",
                            description = "공통 Response",
                            content = [Content(schema = Schema(implementation = BaseResponse::class))]
                        ),
                        ApiResponse(
                            description = "성공시 true",
                            responseCode = "200",
                            content = [Content(schema = Schema(implementation = Boolean::class))]
                        )
                    ],
                    security = [SecurityRequirement(name = "Bearer Authentication")]
                )
            ),
            RouterOperation(
                path = "/users/{userId}/feeds/{feedId}/clap",
                method = [RequestMethod.POST],
                beanClass = FeedHandler::class,
                beanMethod = "clap",
                operation = Operation(
                    tags = ["피드 관리"],
                    summary = "피드 박수 추가",
                    operationId = "clap",
                    parameters = [
                        Parameter(
                            name = "userId",
                            `in` = ParameterIn.PATH,
                            description = "피드에 박수를 추가하는 주체의 userId"
                        ),
                        Parameter(
                            name = "feedId",
                            `in` = ParameterIn.PATH,
                            description = "박수를 추가하려는 피드의 ID"
                        ),
                    ],
                    responses = [
                        ApiResponse(
                            responseCode = "0",
                            description = "공통 Response",
                            content = [Content(schema = Schema(implementation = BaseResponse::class))]
                        ),
                        ApiResponse(
                            description = "성공시 true",
                            responseCode = "200",
                            content = [Content(schema = Schema(implementation = Boolean::class))]
                        )
                    ],
                    security = [SecurityRequirement(name = "Bearer Authentication")]
                )
            ),
            RouterOperation(
                path = "/users/{userId}/feeds/{feedId}/reports",
                method = [RequestMethod.POST],
                beanClass = FeedHandler::class,
                beanMethod = "report",
                operation = Operation(
                    tags = ["피드 관리"],
                    summary = "피드 신고 (신고한 피드에 대한 별도 처리는 없음)",
                    operationId = "report",
                    parameters = [
                        Parameter(
                            name = "userId",
                            `in` = ParameterIn.PATH,
                            description = "피드 신고하는 주체의 userId"
                        ),
                        Parameter(
                            name = "feedId",
                            `in` = ParameterIn.PATH,
                            description = "신고하려는 피드의 ID"
                        ),
                    ],
                    responses = [
                        ApiResponse(
                            responseCode = "0",
                            description = "공통 Response",
                            content = [Content(schema = Schema(implementation = BaseResponse::class))]
                        ),
                        ApiResponse(
                            description = "성공시 true",
                            responseCode = "200",
                            content = [Content(schema = Schema(implementation = Boolean::class))]
                        )
                    ],
                    security = [SecurityRequirement(name = "Bearer Authentication")]
                )
            ),
        ]
    )
    fun feedRouterFunction(): RouterFunction<ServerResponse> =
        router{
            accept(MediaType.APPLICATION_JSON).nest {
                // 피드 목록 조회
                GET("/users/{userId}/feeds", feedHandler::findByUserId)
                // 피드 1건 조회
                GET("/users/{userId}/feeds/{feedId}", feedHandler::findById)
                // 피드 홈 조회
                GET("/users/{userId}/home", feedHandler::home)
                // 피드 저장
                POST("/users/{userId}/feeds", feedHandler::save)
                // 피드 수정
                PATCH("/users/{userId}/feeds/{feedId}", feedHandler::update)
                // 피드 삭제
                DELETE("/users/{userId}/feeds/{feedId}", feedHandler::delete)
                // 클랩
                POST("/users/{userId}/feeds/{feedId}/clap", feedHandler::clap)
                // 신고
                POST("/users/{userId}/feeds/{feedId}/reports", feedHandler::report)
            }
        }
}