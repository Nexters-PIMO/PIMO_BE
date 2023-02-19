package com.nexters.pimo.config

import io.netty.channel.ChannelOption
import io.netty.handler.timeout.ReadTimeoutHandler
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.http.client.reactive.ReactorResourceFactory
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import java.util.concurrent.TimeUnit

/**
 * @author yoonho
 * @since 2023.02.21
 */
@Configuration
class WebClientConfig {

    @Value("\${auth.url}")
    private lateinit var baseUrl: String
    @Value("\${auth.conn.connect-timeout:0.0}")
    private val connectionTimeout: Int? = null
    @Value("\${auth.conn.read-timeout:0.0}")
    private val readTimeout: Long? = null
    @Value("\${auth.conn.user-agent}")
    private lateinit var userAgent: String

    @Bean
    fun defaultWebClient(reactorResourceFactory: ReactorResourceFactory): WebClient {
        val mapper: (HttpClient) -> HttpClient = {
            it.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectionTimeout)
                .doOnConnected {
                    it.addHandlerLast(ReadTimeoutHandler(readTimeout!!, TimeUnit.MILLISECONDS))
                }
        }

        val customExchangeStrategies = ExchangeStrategies.builder()
            .codecs { it.defaultCodecs().maxInMemorySize(1024 * 1024 * 10) }
            .build()

        return WebClient.builder()
            .baseUrl(baseUrl)
            .defaultHeaders {
                it.set("User-Agent", userAgent)
            }
            .clientConnector(ReactorClientHttpConnector(reactorResourceFactory, mapper))
            .exchangeStrategies(customExchangeStrategies)
            .build()
    }
}