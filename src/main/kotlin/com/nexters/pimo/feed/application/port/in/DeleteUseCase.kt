package com.nexters.pimo.feed.application.port.`in`

import reactor.core.publisher.Mono

interface DeleteUseCase {
    fun deleteById(feedId: Long): Mono<Boolean>
}