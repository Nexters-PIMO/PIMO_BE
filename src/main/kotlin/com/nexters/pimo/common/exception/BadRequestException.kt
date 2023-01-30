package com.nexters.pimo.common.exception

/**
 * @author yoonho
 * @since 2023.01.16
 */
class BadRequestException: RuntimeException {
    constructor(msg: String?): super(msg)
    constructor(): super()
}