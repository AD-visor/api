package com.advisor.api.iam.port.inbound.auth.view

import java.time.Instant

data class AuthView(
    val id: Long,
    val oAuthProvider: String,
    val createdAt: Instant
)