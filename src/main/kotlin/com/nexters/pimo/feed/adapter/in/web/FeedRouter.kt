package com.nexters.pimo.feed.adapter.`in`.web

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router

@Configuration
class FeedRouter (
    private val feedHandler: FeedHandler
){
    @Bean
    fun feedRouterFunction(): RouterFunction<ServerResponse> =
        router{
            accept(MediaType.APPLICATION_JSON).nest {
                // 피드 목록 조회
                GET("/users/{userId}/feeds", feedHandler::findByUserId)
                // 피드 1건 조회
                GET("/users/{userId}/feeds/{feedId}", feedHandler::findById)
                // 피드 홈 조회
                GET("/users/{userId}/home", feedHandler::home)
                // 피드 저장
                POST("/users/{userId}/feeds", feedHandler::save)
                // 피드 수정
                PATCH("/users/{userId}/feeds/{feedId}", feedHandler::update)
                // 피드 삭제
                DELETE("/users/{userId}/feeds/{feedId}", feedHandler::delete)
                // 클랩
                POST("/users/{userId}/feeds/{feedId}/clap", feedHandler::clap)
                // 신고
                POST("/users/{userId}/feeds/{feedId}/reports", feedHandler::report)
            }
        }
}