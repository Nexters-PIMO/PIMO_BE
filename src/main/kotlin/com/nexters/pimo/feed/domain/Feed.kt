package com.nexters.pimo.feed.domain

import com.nexters.pimo.feed.application.dto.FeedDto
import com.nexters.pimo.feed.application.dto.UserForFeedDto
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

    @Transient
    var userNickName: String = "",

    @Transient
    var userProfileImgUrl: String = "",

    @Transient
    var contents: List<Content> = listOf(),

    @Transient
    var claps: List<Clap> = listOf()
) {
    fun toDto(userId: String? = null) = FeedDto(
        id = this.id,
        user = UserForFeedDto(this.userId, this.userNickName, this.userProfileImgUrl),
        status = this.status,
        createdAt = this.createdAt.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")),
        clapCount = this.claps.count(),
        clapped = this.claps.any { it.userId == userId },
        contents = this.contents.map { it.toDto() }
    )
}

