package com.nexters.pimo.feed.domain

import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("ClapTB")
data class Clap (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column("userId")
    val userId: String,

    @Column("feedId")
    val feedId: Long,

    @Column("createdAt")
    val createdAt: LocalDateTime = LocalDateTime.now(),
)
