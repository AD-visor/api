package com.advisor.iam.domain.auth

import java.time.Instant

data class AuthView(
    val id: Long,
    val oAuthProvider: String,
    val createdAt: Instant
)
