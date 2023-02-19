package com.nexters.pimo.follow.application.dto

import io.swagger.v3.oas.annotations.media.Schema
import java.io.Serializable

/**
 * @author yoonho
 * @since 2023.02.18
 */
data class FollowAccountDto(
    @Schema(description = "친구 사용자ID", example = "admin1")
    val userId: String,
    @Schema(description = "친구 닉네임", example = "admin1")
    var nickName: String,
    @Schema(description = "친구 프로필사진링크", example = "https://localhost:8080")
    var profileImgUrl: String,
    @Schema(description = "친구 계정상태", example = "0")
    var status: String,
    @Schema(description = "계정 업데이트 일자", example = "yyyyMMddHHmmss")
    var updatedAt: String,
    @Schema(description = "계정 생성 일자", example = "yyyyMMddHHmmss")
    val createdAt: String
): Serializable
