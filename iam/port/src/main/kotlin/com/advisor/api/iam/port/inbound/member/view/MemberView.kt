package com.advisor.api.iam.port.inbound.member.view

import java.time.Instant

data class MemberView(
    val id: Long,
    val email: String,
    val createdAt: Instant,
)