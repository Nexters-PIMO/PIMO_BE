package com.nexters.pimo.feed.application.dto

import java.io.Serializable

data class ContentDto(
    val id: Long,
    val caption: String,
    val url: String,
): Serializable
