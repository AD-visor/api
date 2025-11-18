package com.advisor.api.iam.presentation.member.dto.response

import java.time.Instant

data class MemberResDto(
    val id: Long,
    val email: String,
    val createdAt: Instant
)
