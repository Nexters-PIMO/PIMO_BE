package com.nexters.pimo.config

import io.r2dbc.h2.H2ConnectionConfiguration
import io.r2dbc.h2.H2ConnectionFactory
import io.r2dbc.h2.H2ConnectionOption
import io.r2dbc.spi.ConnectionFactory
import org.h2.tools.Server
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.ContextClosedEvent
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.core.env.Environment
import org.springframework.core.io.ClassPathResource
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator

/**
 * @author yoonho
 * @since 2023.01.16
 */
@EnableR2dbcAuditing
@EnableR2dbcRepositories
@Configuration
class R2dbcConfig(
    private val environment: Environment
): AbstractR2dbcConfiguration() {
    private lateinit var h2Server: Server

    override fun connectionFactory(): ConnectionFactory =
        H2ConnectionFactory(
            H2ConnectionConfiguration.builder()
                .inMemory("testdb")
                .property(H2ConnectionOption.DB_CLOSE_DELAY, "-1")
                .username("sa")
                .build()
        )

    @Bean
    fun h2DbInitializer(): ConnectionFactoryInitializer {
        val initializer = ConnectionFactoryInitializer()
        val resourceDatabasePopulator = ResourceDatabasePopulator()
        resourceDatabasePopulator.addScript(ClassPathResource("h2-schema.sql"));

        initializer.setConnectionFactory(connectionFactory());
        initializer.setDatabasePopulator(resourceDatabasePopulator);
        return initializer;
    }

    @EventListener(ContextRefreshedEvent::class)
    fun start() {
        val profile = environment.activeProfiles
        if(!profile.contains("test")) {
            h2Server = Server.createWebServer("-webPort", "8081")
            h2Server.start()
        }
    }

    @EventListener(ContextClosedEvent::class)
    fun stop() {
        val profile = environment.activeProfiles
        if(!profile.contains("test")) {
            h2Server.stop()
        }
    }
}