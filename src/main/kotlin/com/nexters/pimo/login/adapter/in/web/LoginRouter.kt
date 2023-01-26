package com.nexters.pimo.login.adapter.`in`.web

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router

/**
 * @author yoonho
 * @since 2023.01.24
 */
@Configuration
class LoginRouter(
    private val loginHandler: LoginHandler
) {

    @Bean
    fun loginRouterFunction(): RouterFunction<ServerResponse> =
        router {
            accept(MediaType.APPLICATION_JSON).nest {
                GET("/login", loginHandler::loginPage)
                GET("/login/token", loginHandler::token)
            }
        }
}