package com.nexters.pimo.config

import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing

/**
 * @author yoonho
 * @since 2023.01.16
 */
@EnableR2dbcAuditing
@Configuration
class R2dbcConfig()