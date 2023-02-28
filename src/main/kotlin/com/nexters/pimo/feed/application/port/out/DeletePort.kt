package com.nexters.pimo.feed.application.port.out

import reactor.core.publisher.Mono

interface DeletePort {
    fun deleteById(feedId: Long): Mono<Boolean>
}