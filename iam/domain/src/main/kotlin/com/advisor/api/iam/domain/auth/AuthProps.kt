package com.advisor.api.iam.domain.auth

import com.advisor.api.common.core.domain.vo.identifier.MemberId
import com.advisor.api.iam.domain.auth.vo.OAuthCredential
import com.advisor.api.iam.domain.auth.vo.RefreshToken
import java.time.Instant

data class AuthProps(
    val refreshToken: RefreshToken?,
    val oAuthCredential: OAuthCredential,
    val memberId: MemberId,
    val createdAt: Instant,
    val updatedAt: Instant,
    val isDeleted: Boolean,
    val deletedAt: Instant?
)
