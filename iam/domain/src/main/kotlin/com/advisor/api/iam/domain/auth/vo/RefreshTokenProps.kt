package com.advisor.api.iam.domain.auth.vo

import java.time.Instant

data class RefreshTokenProps (
    val token: String,
    val jti: String,
    val createdAt: Instant,
    val expiresAt: Instant
)
