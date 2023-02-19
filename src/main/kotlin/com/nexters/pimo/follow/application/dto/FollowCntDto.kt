package com.nexters.pimo.follow.application.dto

import io.swagger.v3.oas.annotations.media.Schema
import java.io.Serializable

/**
 * @author yoonho
 * @since 2023.02.16
 */
data class FollowCntDto(
    @Schema(description = "나만 친구 목록", example = "0")
    val followerCnt: Long,
    @Schema(description = "상대방만 친구 목록", example = "0")
    val followeeCnt: Long,
    @Schema(description = "서로 친구 목록", example = "0")
    val followForFollowCnt: Long
): Serializable
