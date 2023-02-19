package com.nexters.pimo.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.security.SecurityScheme
import org.springframework.context.annotation.Configuration

/**
 * @author yoonho
 * @since 2023.02.19
 */
@OpenAPIDefinition(
    info = Info(
        title = "감성팔이소녀 PIMO API 규격서",
        description = "<p>감성팔이소녀팀의 PIMO API 규격서입니다.</p>" +
                "<p>아래 API 문서 참고부탁드리겠습니다.</p>" +
                "<p>문의사항은 언제든 말씀해주세요.</p>",
        version = "0.0.1"
    )
)
@SecurityScheme(
    name = "Bearer Authentication",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    scheme = "bearer"
)
@Configuration
class OpenApiConfig