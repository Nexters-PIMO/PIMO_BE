package com.nexters.pimo.account.adapter.`in`.web.dto

import java.io.Serializable

/**
 * @author yoonho
 * @since 2023.02.14
 */
data class AccountInput (
    val nickName: String,
    val profileImgUrl: String
): Serializable