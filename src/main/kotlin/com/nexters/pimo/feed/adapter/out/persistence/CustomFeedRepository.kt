package com.nexters.pimo.feed.adapter.out.persistence

import com.nexters.pimo.feed.domain.Clap
import com.nexters.pimo.feed.domain.Content
import com.nexters.pimo.feed.domain.Feed
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.stream.Collectors

interface CustomFeedRepository {
    fun findAllByUserIdWithContentAndClap(userId: String): Flux<Feed>

    fun findByIdWithContentAndClap(feedId: Long): Mono<Feed>

    fun findHomeByUserIdWithContentAndClap(userId: String): Flux<Feed>

    fun saveFeed(userId: String): Mono<Long>
}

@Repository
class CustomFeedRepositoryImpl(
    private val client: DatabaseClient
): CustomFeedRepository {

    private val feedMapper: (t: MutableList<MutableMap<String, Any>>) -> Feed
        get() {
            val feedMapper: (t: MutableList<MutableMap<String, Any>>) -> Feed = { list ->
                val feedId = list[0]["id"] as Long
                val userId = list[0]["userId"] as String
                val userNickName = list[0]["nickName"] as String
                val userProfileImgUrl = list[0]["profileImgUrl"] as String
                val contents = list.stream().map {
                    try {
                        val id = it["contentId"] as Long
                        val feedIdForContent = it["feedId"] as Int
                        val caption = it["caption"] as String
                        val url = it["url"] as String
                        val status = it["contentStatus"] as String
                        Content(
                            id = id,
                            caption = caption,
                            url = url,
                            status = status,
                            feedId = feedIdForContent.toLong()
                        )
                    } catch (e: Exception) {
                        null
                    }
                }.collect(Collectors.toList()).filterNotNull().distinctBy{ it.id }
                val claps = list.stream().map {
                    try {
                        val id = it["clapId"] as Long
                        val userIdForClap = it["userId"]
                        val feedIdForClap = it["feedId"] as Int
                        Clap(
                            id = id,
                            feedId = feedIdForClap.toLong(),
                            userId = userIdForClap.toString()
                        )
                    } catch (e: Exception) {
                        null
                    }
                }.collect(Collectors.toList()).filterNotNull().distinctBy{ it.id }
                Feed(id = feedId, userId = userId, contents = contents, claps = claps, userNickName = userNickName, userProfileImgUrl = userProfileImgUrl)
            }
            return feedMapper
        }
    override fun findAllByUserIdWithContentAndClap(userId: String): Flux<Feed> {
        val query = "SELECT" +
                " F.id, F.userId, F.status, F.createdAt,\n" +
                " CT.id AS contentId, CT.feedId, CT.caption, CT.url, CT.status AS contentStatus,\n" +
                " CL.id AS clapId, CL.userId AS clapUserId,\n" +
                " IFNULL(US.nickName, '이름 없음') AS nickName, IFNULL(US.profileImgUrl, '') AS profileImgUrl\n" +
                " FROM FeedTB F\n" +
                " JOIN ContentTB AS CT ON F.id = CT.feedId AND CT.status = '0'\n" +
                " LEFT JOIN ClapTB AS CL ON F.id = CL.feedId\n" +
                " LEFT JOIN UserTB AS US ON F.userId = US.userId AND US.status = '0'\n" +
                " WHERE F.status = '0' AND F.userId = :userId\n" +
                " ORDER BY F.createdAt DESC, F.id DESC;"

        return client.sql(query)
            .bind("userId", userId)
            .fetch().all().bufferUntilChanged { it -> it["id"] }
            .map(feedMapper)
    }

    override fun findByIdWithContentAndClap(feedId: Long): Mono<Feed> {
        val query = "SELECT\n" +
                " F.id, F.userId, F.status, F.createdAt,\n" +
                " CT.id AS contentId, CT.feedId, CT.caption, CT.url, CT.status AS contentStatus,\n" +
                " CL.id AS clapId, CL.userId AS clapUserId,\n" +
                " IFNULL(US.nickName, '이름 없음') AS nickName, IFNULL(US.profileImgUrl, '') AS profileImgUrl\n" +
                " FROM FeedTB F\n" +
                " JOIN ContentTB AS CT ON F.id = CT.feedId AND CT.status = '0'\n" +
                " LEFT JOIN ClapTB CL ON F.id = CL.feedId\n" +
                " LEFT JOIN UserTB AS US ON F.userId = US.userId AND US.status = '0'\n" +
                " WHERE F.id = :feedId;"

        return client.sql(query)
            .bind("feedId", feedId)
            .fetch()
            .all()
            .bufferUntilChanged { it -> it["id"] }
            .map(feedMapper)
            .singleOrEmpty()
    }

    override fun findHomeByUserIdWithContentAndClap(userId: String): Flux<Feed> {
        val query = """
            SELECT T.id, T.userId, T.status, T.createdAt, T.contentId, T.feedId, T.caption, T.url, T.contentStatus, T.clapId, T.clapUserId, T.nickName, T.profileImgUrl
            FROM (
              SELECT
              F.id, F.userId, F.status, F.createdAt,
              CT.id AS contentId, CT.feedId, CT.caption, CT.url, CT.status as contentStatus,
              CL.id AS clapId, CL.userId AS clapUserId,
              IFNULL(US.nickName, '이름 없음') AS nickName, IFNULL(US.profileImgUrl, '') AS profileImgUrl
              FROM FeedTB F
              JOIN FollowTB follow ON follow.followerUserId = F.userId AND follow.followeeUserId = :userId 
              JOIN ContentTB AS CT ON F.id = CT.feedId AND CT.status = '0'
              LEFT JOIN ClapTB CL ON F.id = CL.feedId
              LEFT JOIN UserTB AS US ON F.userId = US.userId AND US.status = '0'
              WHERE F.status = '0'

              UNION ALL 

              SELECT
              F.id, F.userId, F.status, F.createdAt,
              CT.id AS contentId, CT.feedId, CT.caption, CT.url, CT.status AS contentStatus,
              CL.id AS clapId, CL.userId AS clapUserId,
              IFNULL(US.nickName, '이름 없음') AS nickName, IFNULL(US.profileImgUrl, '') AS profileImgUrl
              FROM FeedTB F
              JOIN ContentTB AS CT ON F.id = CT.feedId AND CT.status = '0'
              LEFT JOIN ClapTB AS CL ON F.id = CL.feedId
              LEFT JOIN UserTB AS US ON F.userId = US.userId AND US.status = '0'
              WHERE F.status = '0' AND F.userId = :userId
            ) T
            ORDER BY T.createdAt DESC, T.id DESC;
        """.trimIndent()

        return client.sql(query)
            .bind("userId", userId)
            .fetch().all().bufferUntilChanged { it -> it["id"] }
            .map(feedMapper)
    }

    override fun saveFeed(userId: String): Mono<Long> {
        val query = "INSERT INTO FeedTB(userId, status) VALUES(:userId, '0');"
        return client.sql(query)
            .filter {statement, _ -> statement.returnGeneratedValues("id").execute()}
            .bind("userId", userId)
            .fetch()
            .first()
            .map { it["id"] as Long }
    }

}