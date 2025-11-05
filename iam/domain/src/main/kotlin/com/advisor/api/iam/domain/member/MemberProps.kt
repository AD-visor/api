package com.advisor.api.iam.domain.member

import com.advisor.api.iam.domain.member.vo.Email
import java.time.Instant

data class MemberProps(
    val email: Email,
    val createdAt: Instant,
    val updatedAt: Instant,
    val isDeleted: Boolean = false,
    val deletedAt: Instant? = null,
)
