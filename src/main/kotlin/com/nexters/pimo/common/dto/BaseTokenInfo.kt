package com.nexters.pimo.common.dto

import io.swagger.v3.oas.annotations.media.Schema
import java.io.Serializable

/**
 * @author yoonho
 * @since 2023.02.14
 */
data class BaseTokenInfo (
    @Schema(description = "소셜로그인 구분", example = "kakao")
    val provider: String,
    @Schema(description = "토큰", example = "0")
    val accessToken: String
): Serializable