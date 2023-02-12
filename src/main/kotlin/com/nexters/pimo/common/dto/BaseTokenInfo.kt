package com.nexters.pimo.common.dto

import java.io.Serializable

/**
 * @author yoonho
 * @since 2023.02.14
 */
data class BaseTokenInfo (
    val provider: String,
    val accessToken: String
): Serializable