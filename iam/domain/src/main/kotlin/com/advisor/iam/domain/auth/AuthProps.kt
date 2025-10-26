package com.advisor.iam.domain.auth

import com.advisor.api.common.core.domain.vo.identifier.UserId
import com.advisor.iam.domain.auth.vo.OAuthCredential
import com.advisor.iam.domain.auth.vo.RefreshToken
import java.time.Instant

data class AuthProps(
    val refreshToken: RefreshToken,
    val oAuthCredential: OAuthCredential,
    val userId: UserId,
    val createdAt: Instant,
    val updatedAt: Instant,
    val isDeleted: Boolean,
    val deletedAt: Instant?
)
