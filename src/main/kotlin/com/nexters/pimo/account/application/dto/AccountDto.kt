package com.nexters.pimo.account.application.dto

import io.swagger.v3.oas.annotations.media.Schema

/**
 * @author yoonho
 * @since 2023.02.15
 */
data class AccountDto(
    @Schema(description = "사용자ID", example = "admin1")
    val userId: String,
    @Schema(description = "닉네임", example = "admin1")
    var nickName: String,
    @Schema(description = "프로필사진링크", example = "https://localhost:8080")
    var profileImgUrl: String,
    @Schema(description = "계정상태", example = "0")
    var status: String,
    @Schema(description = "계정 업데이트 일자", example = "yyyyMMddHHmmss")
    var updatedAt: String,
    @Schema(description = "계정 생성 일자", example = "yyyyMMddHHmmss")
    val createdAt: String
)