package com.advisor.api.iam.domain.member

import java.time.Instant

data class MemberView(
    val id: Long,
    val email: String,
    val createdAt: Instant,
)
