package com.nexters.pimo.feed.application.dto

import java.io.Serializable

data class UserForFeedDto(
   val userId: String,
   val nickName: String,
   val profileImgUrl: String,
): Serializable
