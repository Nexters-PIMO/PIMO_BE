package com.nexters.pimo.feed.adapter.`in`.web.dto

import java.io.Serializable

data class FeedInput(
    val userId: String,
    val contents: List<ContentInput>
): Serializable

