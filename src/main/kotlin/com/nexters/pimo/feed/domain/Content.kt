package com.nexters.pimo.feed.domain

import com.nexters.pimo.common.constants.CommCode
import jakarta.persistence.*
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table(name = "ContentTB")
data class Content(
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "feedId", nullable = false)
//    val feed: Feed,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column("text")
    val text: String,

    @Column("url")
    val url: String,

    @Column("status")
    val status: String = "0",

    @Column("createdAt")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column("updatedAt")
    val updatedAt: LocalDateTime = LocalDateTime.now(),

    @Column("deletedAt")
    val deletedAt: LocalDateTime? = null
)
