package com.advisor.api.iam.adapter.outbound.auth

import com.advisor.api.common.exception.CustomException
import com.advisor.api.iam.domain.auth.vo.RefreshToken
import com.advisor.api.iam.domain.auth.vo.RefreshTokenProps
import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.time.Instant

@Embeddable
class RefreshTokenEmbeddable (
    @Column(name = "refresh_token_token")
    val token: String?,

    @Column(name = "refresh_token_jti")
    val jti: String?,

    @Column(name = "refresh_token_created_at")
    val createdAt: Instant?,

    @Column(name = "refresh_token_expires_at")
    val expiresAt: Instant?
) {
    companion object {
        fun toPersistence(refreshToken: RefreshToken): RefreshTokenEmbeddable {
            return RefreshTokenEmbeddable(
                token = refreshToken.token,
                jti = refreshToken.jti,
                createdAt = refreshToken.createdAt,
                expiresAt = refreshToken.expiresAt
            )
        }
    }

    fun toDomain(): RefreshToken? {
        if (token == null && jti == null && createdAt == null && expiresAt == null) {
            return null
        }

        if (token == null || jti == null || createdAt == null || expiresAt == null) {
            throw CustomException(
                AuthInfrastructureExceptionCode.AUTH_REFRESH_TOKEN_PERSISTENCE_ERROR,
                "[Auth] RefreshToken 데이터가 불완전합니다."
            )
        }

        return RefreshToken.create(
            RefreshTokenProps(
                token = requireNotNull(token),
                jti = requireNotNull(jti),
                createdAt = requireNotNull(createdAt),
                expiresAt = requireNotNull(expiresAt)
            )
        )
    }
}
