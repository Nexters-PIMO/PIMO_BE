package com.nexters.pimo.follow.application.dto

import java.io.Serializable

/**
 * @author yoonho
 * @since 2023.02.18
 */
data class FollowAccountDto(
    val userId: String,
    var nickName: String,
    var profileImgUrl: String,
    var status: String,
    var updatedAt: String,
    val createdAt: String
): Serializable
