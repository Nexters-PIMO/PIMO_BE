package com.nexters.pimo.feed.application.dto

import java.io.Serializable

data class FeedDto(
    val id: Long,
    val status: String,
    val createdAt: String,
    val clapCount: Int,
    val clapped: Boolean,
    val contents: List<ContentDto>,
    val user: UserForFeedDto,
): Serializable
