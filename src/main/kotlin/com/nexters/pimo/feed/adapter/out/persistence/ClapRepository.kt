package com.nexters.pimo.feed.adapter.out.persistence

import com.nexters.pimo.feed.domain.Clap
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux

interface ClapRepository: ReactiveCrudRepository<Clap, Long>  {
    fun findAllByFeedId(feedId: Long): Flux<Clap>
}