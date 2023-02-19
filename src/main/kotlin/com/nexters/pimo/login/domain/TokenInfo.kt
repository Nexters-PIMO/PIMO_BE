package com.nexters.pimo.login.domain

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema

/**
 * @author yoonho
 * @since 2023.01.26
 */
data class TokenInfo(
    @Schema(description = "access_token", example = "0")
    @JsonProperty("access_token")
    val accessToken: String,
    @Schema(description = "refresh_token", example = "0")
    @JsonProperty("refresh_token")
    val refreshToken: String
)