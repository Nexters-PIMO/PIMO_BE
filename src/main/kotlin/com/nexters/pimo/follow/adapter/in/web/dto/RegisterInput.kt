package com.nexters.pimo.follow.adapter.`in`.web.dto

import io.swagger.v3.oas.annotations.media.Schema
import java.io.Serializable

/**
 * @author yoonho
 * @since 2023.02.16
 */
data class RegisterInput(
   @Schema(description = "사용자 ID", example = "admin1", required = true)
    val userId: String
): Serializable