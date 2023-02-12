package com.nexters.pimo.login.adapter.out.dto

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * @author yoonho
 * @since 2023.02.12
 */
data class KakaoTokenInfo(
    val id: String,
    @JsonProperty("expires_in")
    var expiresIn: Int?,
    @JsonProperty("expiresInMillis")
    var expiresInMillis: Int?,
    @JsonProperty("appId")
    var appId: Int?,

    val code: Int?,
    val msg: String?,
)
