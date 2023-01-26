package com.nexters.pimo.login.domain

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * @author yoonho
 * @since 2023.01.26
 */
data class TokenInfo(
    @JsonProperty("access_token")
    val accessToken: String,
    @JsonProperty("refresh_token")
    val refreshToken: String
)