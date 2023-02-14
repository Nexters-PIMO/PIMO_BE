package com.nexters.pimo.account.adapter.`in`.web

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router

/**
 * @author yoonho
 * @since 2023.02.14
 */
@Configuration
class AccountRouter(
    private val accountHandler: AccountHandler
) {
    @Bean
    fun accountRouterFunction(): RouterFunction<ServerResponse> =
        router {
            accept(MediaType.APPLICATION_JSON).nest {
                GET("/users", accountHandler::findMe)
                GET("/users/exists", accountHandler::existUser)
                GET("/users/exists/nickname", accountHandler::existsNickName)
                GET("/users/search", accountHandler::findUser)
                GET("/users/search/nickname", accountHandler::searchUser)
                POST("/users", accountHandler::saveUser)
                PATCH("/users/{target}", accountHandler::updateUser)
                DELETE("/users", accountHandler::deleteUser)
            }
        }
}