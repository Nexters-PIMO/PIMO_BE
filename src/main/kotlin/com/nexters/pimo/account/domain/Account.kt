package com.nexters.pimo.account.domain

import com.nexters.pimo.account.application.dto.AccountDto
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * @author yoonho
 * @since 2023.02.14
 */
@Table(name = "UserTB")
data class Account(
    @Id
    @Column("id")
    val id: Long = 0,
    @Column("userId")
    val userId: String,
    @Column("nickName")
    var nickName: String,
    @Column("profileImgUrl")
    var profileImgUrl: String,
    @Column("status")
    var status: String,
    @Column("updatedAt")
    var updatedAt: LocalDateTime,
    @Column("createdAt")
    val createdAt: LocalDateTime = LocalDateTime.now()
) {
    fun toDto() = AccountDto(
        userId = this.userId,
        nickName = this.nickName,
        profileImgUrl = this.profileImgUrl,
        status = this.status,
        updatedAt = this.updatedAt.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")),
        createdAt = this.createdAt.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
    )

    fun changeNickName(nickName: String): Account {
        this.updatedAt = LocalDateTime.now()
        this.nickName = nickName
        return this
    }

    fun changeProfileImgUrl(profileImgUrl: String): Account {
        this.updatedAt = LocalDateTime.now()
        this.profileImgUrl = profileImgUrl
        return this
    }
}