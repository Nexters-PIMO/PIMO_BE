package com.nexters.pimo.follow.adapter.`in`.web

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router

/**
 * @author yoonho
 * @since 2023.02.15
 */
@Configuration
class FollowRouter(
    private val followHandler: FollowHandler
) {

    @Bean
    fun followRouterFunction(): RouterFunction<ServerResponse> =
        router {
            accept(MediaType.APPLICATION_JSON).nest {
                POST("/follows", followHandler::register)
                DELETE("/follows", followHandler::delete)
                GET("/follows/count", followHandler::count)
                GET("/follows/{target}", followHandler::list)
            }
        }
}