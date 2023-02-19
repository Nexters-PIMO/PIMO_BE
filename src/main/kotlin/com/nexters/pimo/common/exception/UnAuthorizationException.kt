package com.nexters.pimo.common.exception

/**
 * @author yoonho
 * @since 2023.02.19
 */
class UnAuthorizationException: RuntimeException {
    constructor(msg: String?): super(msg)
    constructor(): super()
}