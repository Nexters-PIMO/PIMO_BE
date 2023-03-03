package com.nexters.pimo.feed.adapter.out.persistence

import com.nexters.pimo.feed.domain.Feed
import com.nexters.pimo.feed.domain.Report
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface ReportRepository : ReactiveCrudRepository<Report, Long> {
}