package com.nexters.pimo.follow.domain

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

/**
 * @author yoonho
 * @since 2023.02.16
 */
@Table(name = "FollowTB")
data class Follow (
    @Id
    @Column("id")
    val id: Long = 0,
    @Column("followerUserId")
    val followerUserId: String,
    @Column("followerNickName")
    val followerNickName: String,
    @Column("followeeUserId")
    val followeeUserId: String,
    @Column("followeeNickName")
    val followeeNickName: String,
    @Column("createdAt")
    val createdAt: LocalDateTime = LocalDateTime.now()
)