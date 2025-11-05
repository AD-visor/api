package com.advisor.api.iam.domain.auth

import java.time.Instant

data class AuthView(
    val id: Long,
    val oAuthProvider: String,
    val createdAt: Instant
)
