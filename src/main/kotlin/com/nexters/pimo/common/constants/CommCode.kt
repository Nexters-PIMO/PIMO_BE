package com.nexters.pimo.common.constants

/**
 * @author yoonho
 * @since 2023.01.26
 */
class CommCode {
    enum class Social(val code: String, val prefix: String){
        KAKAO("kakao", "k::"),
        APPLE("apple", "a::")
    }
}