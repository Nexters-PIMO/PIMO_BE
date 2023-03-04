package com.nexters.pimo.feed.domain

import com.nexters.pimo.feed.application.dto.ContentDto
import jakarta.persistence.*
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table(name = "ContentTB")
data class Content(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column("caption")
    val caption: String,

    @Column("url")
    val url: String,

    @Column("feedId")
    val feedId: Long,

    @Column("status")
    val status: String = "0",

    @Column("createdAt")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column("updatedAt")
    val updatedAt: LocalDateTime = LocalDateTime.now(),

) {
    fun toDto(): ContentDto {
        return ContentDto(
            id = id,
            caption = caption,
            url = url
        )
    }
}
