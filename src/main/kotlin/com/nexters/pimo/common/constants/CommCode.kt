package com.nexters.pimo.common.constants

import com.nexters.pimo.common.exception.BadRequestException

/**
 * @author yoonho
 * @since 2023.01.26
 */
class CommCode {
    companion object {
        fun findPrefix(type: String): String {
            for(item in Social.values()) {
                if(item.code == type) {
                    return item.prefix
                }
            }

            throw BadRequestException("지원하지 않는 소셜로그인입니다.")
        }
    }

    enum class Social(val code: String, val prefix: String){
        KAKAO("kakao", "K"),
        APPLE("apple", "A")
    }
}