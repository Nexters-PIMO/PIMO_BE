package com.nexters.pimo.common.handler

import com.fasterxml.jackson.databind.ObjectMapper
import com.nexters.pimo.common.dto.BaseResponse
import com.nexters.pimo.common.exception.BadRequestException
import com.nexters.pimo.common.exception.NotFoundDataException
import org.slf4j.LoggerFactory
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

/**
 * @author yoonho
 * @since 2023.01.16
 */
@Component
@Order(-2)
class ExceptionHandler: ErrorWebExceptionHandler {
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
        private val objectMapper = ObjectMapper()
    }

    override fun handle(exchange: ServerWebExchange, ex: Throwable): Mono<Void> {
        when(ex) {
            is NotFoundDataException -> {
                log.warn(" >>> [handle] NotFoundDataException occurs - message: {}", ex.message)
                return exchange.response.writeWith(Mono.fromSupplier {
                    val bufferFactory = exchange.response.bufferFactory()
                    exchange.response.statusCode = HttpStatus.BAD_REQUEST
                    exchange.response.headers.contentType = MediaType.APPLICATION_JSON
                    return@fromSupplier bufferFactory.wrap(
                        objectMapper.writeValueAsBytes(BaseResponse(ex.message, HttpStatus.BAD_REQUEST, null))
                    )
                })
            }
            is BadRequestException -> {
                log.warn(" >>> [handle] BadRequestException occurs - message: {}", ex.message)
                return exchange.response.writeWith(Mono.fromSupplier {
                    val bufferFactory = exchange.response.bufferFactory()
                    exchange.response.statusCode = HttpStatus.BAD_REQUEST
                    exchange.response.headers.contentType = MediaType.APPLICATION_JSON
                    return@fromSupplier bufferFactory.wrap(
                        objectMapper.writeValueAsBytes(BaseResponse(ex.message, HttpStatus.BAD_REQUEST, null))
                    )
                })
            }
            is IllegalArgumentException -> {
                log.warn(" >>> [handle] IllegalArgumentException occurs - message: {}", ex.message)
                return exchange.response.writeWith(Mono.fromSupplier {
                    val bufferFactory = exchange.response.bufferFactory()
                    exchange.response.statusCode = HttpStatus.BAD_REQUEST
                    exchange.response.headers.contentType = MediaType.APPLICATION_JSON
                    return@fromSupplier bufferFactory.wrap(
                        objectMapper.writeValueAsBytes(BaseResponse(ex.message, HttpStatus.BAD_REQUEST, null))
                    )
                })
            }
            else -> {
                return exchange.response.writeWith(Mono.fromSupplier {
                    val bufferFactory = exchange.response.bufferFactory()
                    exchange.response.statusCode = HttpStatus.BAD_REQUEST
                    exchange.response.headers.contentType = MediaType.APPLICATION_JSON
                    return@fromSupplier bufferFactory.wrap(
                        objectMapper.writeValueAsBytes(BaseResponse(ex.message, HttpStatus.BAD_REQUEST, null))
                    )
                })
            }
        }
    }
}