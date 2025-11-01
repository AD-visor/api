package com.advisor.iam.domain.member

import com.advisor.iam.domain.member.vo.Email
import java.time.Instant

data class MemberProps(
    val email: Email,
    val createdAt: Instant,
    val updatedAt: Instant,
    val isDeleted: Boolean,
    val deletedAt: Instant?,
)
