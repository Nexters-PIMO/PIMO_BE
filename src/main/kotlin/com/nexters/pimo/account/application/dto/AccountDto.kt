package com.nexters.pimo.account.application.dto

/**
 * @author yoonho
 * @since 2023.02.15
 */
data class AccountDto(
    val userId: String,
    var nickName: String,
    var profileImgUrl: String,
    var status: String,
    var updatedAt: String,
    val createdAt: String
)