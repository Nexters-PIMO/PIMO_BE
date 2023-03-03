package com.nexters.pimo.feed.domain

import com.nexters.pimo.common.constants.CommCode
import com.nexters.pimo.feed.adapter.`in`.web.dto.ContentInput
import com.nexters.pimo.feed.application.dto.FeedDto
import jakarta.persistence.*
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Table(name = "FeedTB")
data class Feed(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column("userId")
    val userId: String,

    @Column("status")
    val status: String = "0",

    @Column("createdAt")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column("updatedAt")
    val updatedAt: LocalDateTime = LocalDateTime.now(),

//    @Column("deletedAt")
//    val deletedAt: LocalDateTime? = null,
) {
//    @OneToMany(
//        mappedBy = "feed",
//        fetch = FetchType.LAZY,
////        cascade = [CascadeType.PERSIST, CascadeType.MERGE]
//        cascade = [CascadeType.ALL]
//    )
//    @JoinColumn(name = "feedId")
//    lateinit var contents: List<Content>
////        private set

//    @OneToMany(
//        mappedBy = "Feed",
//        fetch = FetchType.LAZY,
////        cascade = [CascadeType.PERSIST, CascadeType.MERGE]
//    )
////    @JoinColumn(name = "feedId")
//    lateinit var claps: List<Clap>
//        private set

//    constructor(userId: String, contents: List<ContentInput>): this(userId = userId) {
//        this.contents = contents.map {
//            Content(
//                url = it.url,
//                caption = it.caption,
//                feed = this
//            )
//        }
//    }

//    lateinit var claps: List<Clap>
//    lateinit var contents: List<Content>

    //    fun toDto(contents: List<Content>, claps: List<Clap> = listOf(), userId: String? = null) = FeedDto(
    fun toDto(userId: String? = null) = FeedDto(
        id = this.id,
        userId = this.userId,
        status = this.status,
        createdAt = this.createdAt.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")),
//        clapCount = claps?.count() ?: 0,
//        clapped = claps?.any { it.userId == userId } ?: false,
//        contents = contents ?: listOf()
        clapCount = 0,
        clapped = false,
        contents = listOf()
    )

//    fun updateContents(contents: List<ContentInput>): Feed {
//        this.contents = contents.map {
//            Content(
//                url = it.url,
//                caption = it.caption,
//                feed = this
//            )
//        }
//
//        return this
//    }
}