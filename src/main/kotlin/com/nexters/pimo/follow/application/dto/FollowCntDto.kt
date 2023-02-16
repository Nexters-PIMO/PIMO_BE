package com.nexters.pimo.follow.application.dto

import java.io.Serializable

/**
 * @author yoonho
 * @since 2023.02.16
 */
data class FollowCntDto(
    val followerCnt: Long,
    val followeeCnt: Long,
    val followForFollowCnt: Long
): Serializable
