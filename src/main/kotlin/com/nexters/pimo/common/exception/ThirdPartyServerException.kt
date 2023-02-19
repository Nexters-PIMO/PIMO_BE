package com.nexters.pimo.common.exception

/**
 * @author yoonho
 * @since 2023.02.21
 */
class ThirdPartyServerException: RuntimeException {
    constructor(msg: String?): super(msg)
    constructor(): super()
}