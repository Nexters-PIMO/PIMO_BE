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
data class Feed (
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

    @Column("deletedAt")
    val deletedAt: LocalDateTime? = null,
) {
    @OneToMany(
        mappedBy = "feed",
        fetch = FetchType.LAZY,
//        cascade = [CascadeType.PERSIST, CascadeType.MERGE]
    )
    @JoinColumn(name = "feedId")
    lateinit var contents: List<Content>
//        private set

//    @OneToMany(
//        mappedBy = "Feed",
//        fetch = FetchType.LAZY,
////        cascade = [CascadeType.PERSIST, CascadeType.MERGE]
//    )
////    @JoinColumn(name = "feedId")
//    lateinit var claps: List<Clap>
//        private set

    constructor(userId: String, contents: List<ContentInput>): this(userId = userId) {
        this.contents = contents.map {
            Content(
                url = it.url,
                text = it.text,
//                feed = this
            )
        }
    }

    fun toDto(userId: String? = null) = FeedDto(
        id = this.id,
        userId = this.userId,
        status = this.status,
        createdAt = this.createdAt.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")),
//        clapCount = this.claps.count(),
//        clapped = this.claps.any { it.userId == userId }
        clapCount = 0,
        clapped = false
    )

    fun updateContents(contents: List<ContentInput>): Feed {
        this.contents = contents.map {
            Content(
                url = it.url,
                text = it.text,
//                feed = this
            )
        }

        return this
    }
}