package com.nexters.pimo.common.dto

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerResponse
import java.io.Serializable

/**
 * @author yoonho
 * @since 2023.01.16
 */
@Schema(description = "공통 Response")
data class BaseResponse (
    @Schema(description = "응답 메세지", example = "Success")
    val message: String?,
    @Schema(description = "응답 HTTP Status", example = "200")
    val status: HttpStatus,
    @Schema(description = "응답 데이터")
    val data: Any?
): Serializable {
    constructor(): this(message = "Success", status = HttpStatus.OK, null)
    constructor(message: String?, status: HttpStatus): this(message = message, status = status, null)

    fun success(data: Any?) =
        ServerResponse.ok().body(
            BodyInserters.fromValue(
                BaseResponse(
                    message = "Success",
                    status = HttpStatus.OK,
                    data
                )
            )
        )
}