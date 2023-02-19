package com.nexters.pimo.account.adapter.`in`.web

import com.nexters.pimo.account.adapter.`in`.web.dto.AccountInput
import com.nexters.pimo.account.application.dto.AccountDto
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
 * @since 2023.02.14
 */
@Configuration
class AccountRouter(
    private val accountHandler: AccountHandler
) {
    @Bean
    @RouterOperations(
        value = [
            RouterOperation(
                path = "/users",
                method = [RequestMethod.GET],
                beanClass = AccountHandler::class,
                beanMethod = "findMe",
                operation = Operation(
                    tags = ["계정관리"],
                    summary = "나의 계정정보 조회",
                    operationId = "findMe",
                    responses = [
                        // 공통 Response(BaseResponse)객체를 노출하기 위해 사용, responseCode는 swagger에서 맨 앞단에 노출시키기 위해 "0" 고정
                        ApiResponse(
                            responseCode = "0",
                            description = "공통 Response",
                            content = [Content(schema = Schema(implementation = BaseResponse::class))]
                        ),
                        ApiResponse(
                            description = "나의 계정정보",
                            responseCode = "200",
                            content = [Content(schema = Schema(implementation = AccountDto::class))]
                        )
                    ],
                    security = [SecurityRequirement(name = "Bearer Authentication")]
                )
            ),
            RouterOperation(
                path = "/users/exists",
                method = [RequestMethod.GET],
                beanClass = AccountHandler::class,
                beanMethod = "existUser",
                operation = Operation(
                    tags = ["계정관리"],
                    summary = "나의 계정정보 존재여부 조회",
                    operationId = "existUser",
                    responses = [
                        // 공통 Response(BaseResponse)객체를 노출하기 위해 사용, responseCode는 swagger에서 맨 앞단에 노출시키기 위해 "0" 고정
                        ApiResponse(
                            responseCode = "0",
                            description = "공통 Response",
                            content = [Content(schema = Schema(implementation = BaseResponse::class))]
                        ),
                        ApiResponse(
                            description = "나의 계정정보 존재여부",
                            responseCode = "200",
                            content = [Content(schema = Schema(implementation = Boolean::class))]
                        )
                    ],
                    security = [SecurityRequirement(name = "Bearer Authentication")]
                )
            ),
            RouterOperation(
                path = "/users/exists/nickname",
                method = [RequestMethod.GET],
                beanClass = AccountHandler::class,
                beanMethod = "existsNickName",
                operation = Operation(
                    tags = ["계정관리"],
                    summary = "닉네임 존재여부 조회(중복체크)",
                    operationId = "existsNickName",
                    parameters = [
                        Parameter(name = "nickname", description = "닉네임", example = "admin", required = true),
                    ],
                    responses = [
                        // 공통 Response(BaseResponse)객체를 노출하기 위해 사용, responseCode는 swagger에서 맨 앞단에 노출시키기 위해 "0" 고정
                        ApiResponse(
                            responseCode = "0",
                            description = "공통 Response",
                            content = [Content(schema = Schema(implementation = BaseResponse::class))]
                        ),
                        ApiResponse(
                            description = "닉네임 존재여부",
                            responseCode = "200",
                            content = [Content(schema = Schema(implementation = Boolean::class))]
                        )
                    ],
                    security = [SecurityRequirement(name = "Bearer Authentication")]
                )
            ),
            RouterOperation(
                path = "/users/search",
                method = [RequestMethod.GET],
                beanClass = AccountHandler::class,
                beanMethod = "findUser",
                operation = Operation(
                    tags = ["계정관리"],
                    summary = "사용자 계정정보 조회",
                    operationId = "findUser",
                    parameters = [
                        Parameter(name = "userId", description = "사용자 ID", example = "admin", required = true),
                    ],
                    responses = [
                        // 공통 Response(BaseResponse)객체를 노출하기 위해 사용, responseCode는 swagger에서 맨 앞단에 노출시키기 위해 "0" 고정
                        ApiResponse(
                            responseCode = "0",
                            description = "공통 Response",
                            content = [Content(schema = Schema(implementation = BaseResponse::class))]
                        ),
                        ApiResponse(
                            description = "조회한 사용자 계정정보",
                            responseCode = "200",
                            content = [Content(schema = Schema(implementation = AccountDto::class))]
                        )
                    ],
                    security = [SecurityRequirement(name = "Bearer Authentication")]
                )
            ),
            RouterOperation(
                path = "/users/search/nickname",
                method = [RequestMethod.GET],
                beanClass = AccountHandler::class,
                beanMethod = "searchUser",
                operation = Operation(
                    tags = ["계정관리"],
                    summary = "사용자 닉네임 조회",
                    operationId = "searchUser",
                    parameters = [
                        Parameter(name = "nickname", description = "닉네임", example = "admin", required = true),
                    ],
                    responses = [
                        // 공통 Response(BaseResponse)객체를 노출하기 위해 사용, responseCode는 swagger에서 맨 앞단에 노출시키기 위해 "0" 고정
                        ApiResponse(
                            responseCode = "0",
                            description = "공통 Response",
                            content = [Content(schema = Schema(implementation = BaseResponse::class))]
                        ),
                        ApiResponse(
                            description = "조회한 사용자 계정정보",
                            responseCode = "200",
                            content = [Content(schema = Schema(implementation = AccountDto::class))]
                        )
                    ],
                    security = [SecurityRequirement(name = "Bearer Authentication")]
                )
            ),
            RouterOperation(
                path = "/users",
                method = [RequestMethod.POST],
                beanClass = AccountHandler::class,
                beanMethod = "saveUser",
                operation = Operation(
                    tags = ["계정관리"],
                    summary = "사용자 정보 저장",
                    operationId = "saveUser",
                    requestBody = RequestBody(
                        content = [Content(schema = Schema(implementation = AccountInput::class, required = true))]
                    ),
                    responses = [
                        // 공통 Response(BaseResponse)객체를 노출하기 위해 사용, responseCode는 swagger에서 맨 앞단에 노출시키기 위해 "0" 고정
                        ApiResponse(
                            responseCode = "0",
                            description = "공통 Response",
                            content = [Content(schema = Schema(implementation = BaseResponse::class))]
                        ),
                        ApiResponse(
                            description = "저장한 사용자 계정정보",
                            responseCode = "200",
                            content = [Content(schema = Schema(implementation = AccountDto::class))]
                        )
                    ],
                    security = [SecurityRequirement(name = "Bearer Authentication")]
                )
            ),
            RouterOperation(
                path = "/users/nickname",
                method = [RequestMethod.PATCH],
                beanClass = AccountHandler::class,
                beanMethod = "updateUser",
                operation = Operation(
                    tags = ["계정관리"],
                    summary = "사용자 닉네임 정보 업데이트",
                    operationId = "updateUser",
                    parameters = [
                        Parameter(name = "nickname", description = "닉네임", example = "admin", required = true),
                    ],
                    responses = [
                        // 공통 Response(BaseResponse)객체를 노출하기 위해 사용, responseCode는 swagger에서 맨 앞단에 노출시키기 위해 "0" 고정
                        ApiResponse(
                            responseCode = "0",
                            description = "공통 Response",
                            content = [Content(schema = Schema(implementation = BaseResponse::class))]
                        ),
                        ApiResponse(
                            description = "업데이트한 사용자 계정정보",
                            responseCode = "200",
                            content = [Content(schema = Schema(implementation = AccountDto::class))]
                        )
                    ],
                    security = [SecurityRequirement(name = "Bearer Authentication")]
                )
            ),
            RouterOperation(
                path = "/users/profile",
                method = [RequestMethod.PATCH],
                beanClass = AccountHandler::class,
                beanMethod = "updateUser",
                operation = Operation(
                    tags = ["계정관리"],
                    summary = "사용자 프로필사진링크 정보 업데이트",
                    operationId = "updateUser",
                    parameters = [
                        Parameter(name = "profile", description = "프로필사진링크", example = "http://localhost:8080", required = true),
                    ],
                    responses = [
                        // 공통 Response(BaseResponse)객체를 노출하기 위해 사용, responseCode는 swagger에서 맨 앞단에 노출시키기 위해 "0" 고정
                        ApiResponse(
                            responseCode = "0",
                            description = "공통 Response",
                            content = [Content(schema = Schema(implementation = BaseResponse::class))]
                        ),
                        ApiResponse(
                            description = "업데이트한 사용자 계정정보",
                            responseCode = "200",
                            content = [Content(schema = Schema(implementation = AccountDto::class))]
                        )
                    ],
                    security = [SecurityRequirement(name = "Bearer Authentication")]
                )
            ),
            RouterOperation(
                path = "/users",
                method = [RequestMethod.DELETE],
                beanClass = AccountHandler::class,
                beanMethod = "deleteUser",
                operation = Operation(
                    tags = ["계정관리"],
                    summary = "사용자 정보 삭제",
                    operationId = "deleteUser",
                    responses = [
                        // 공통 Response(BaseResponse)객체를 노출하기 위해 사용, responseCode는 swagger에서 맨 앞단에 노출시키기 위해 "0" 고정
                        ApiResponse(
                            responseCode = "0",
                            description = "공통 Response",
                            content = [Content(schema = Schema(implementation = BaseResponse::class))]
                        ),
                        ApiResponse(
                            description = "사용자 삭제 성공여부",
                            responseCode = "200",
                            content = [Content(schema = Schema(implementation = Boolean::class))]
                        )
                    ],
                    security = [SecurityRequirement(name = "Bearer Authentication")]
                )
            ),
        ]
    )
    fun accountRouterFunction(): RouterFunction<ServerResponse> =
        router {
            accept(MediaType.APPLICATION_JSON).nest {
                GET("/users", accountHandler::findMe)
                GET("/users/exists", accountHandler::existUser)
                GET("/users/exists/nickname", accountHandler::existsNickName)
                GET("/users/search", accountHandler::findUser)
                GET("/users/search/nickname", accountHandler::searchUser)
                POST("/users", accountHandler::saveUser)
                PATCH("/users/{target}", accountHandler::updateUser)
                DELETE("/users", accountHandler::deleteUser)
            }
        }
}