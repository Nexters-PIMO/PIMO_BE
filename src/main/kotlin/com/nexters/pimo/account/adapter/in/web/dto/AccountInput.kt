package com.nexters.pimo.account.adapter.`in`.web.dto

import io.swagger.v3.oas.annotations.media.Schema
import java.io.Serializable

/**
 * @author yoonho
 * @since 2023.02.14
 */
data class AccountInput (
    @Schema(description = "닉네임", example = "admin1")
    val nickName: String,
    @Schema(description = "프로필사진링크", example = "https://localhost:8080")
    val profileImgUrl: String
): Serializable