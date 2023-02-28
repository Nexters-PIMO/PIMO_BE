package com.nexters.pimo.feed.adapter.out.persistence

import com.nexters.pimo.feed.domain.Clap
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface ClapRepository: ReactiveCrudRepository<Clap, Long>  {
}