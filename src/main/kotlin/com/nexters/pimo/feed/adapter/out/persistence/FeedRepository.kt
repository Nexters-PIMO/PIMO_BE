package com.nexters.pimo.feed.adapter.out.persistence

import com.nexters.pimo.feed.domain.Feed
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface FeedRepository : ReactiveCrudRepository<Feed, Long>, CustomFeedRepository {
    fun findAllByUserIdAndStatus(userId: String, status: String): Flux<Feed>
    fun findByIdAndStatus(id: Long, status: String): Mono<Feed>
    fun findFeedByIdAndStatus(id: Long, status: String): Mono<Feed>

//    @Query(
//        "select feed.id, feed.userId, feed.status, feed.updatedAt, feed.createdAt " +
//                "from FeedTB feed " +
//                "join FollowTB follow " +
//                "on follow.followerUserId = feed.userId and follow.followeeUserId = :userId " +
//                "where feed.status = '0' "+
//                "order by feed.createdAt"
//    )
//    fun findHomeByUserId(userId: String): Flux<Feed>
}