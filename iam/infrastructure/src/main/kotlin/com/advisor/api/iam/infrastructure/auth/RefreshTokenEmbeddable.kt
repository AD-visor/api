package com.advisor.api.iam.infrastructure.auth

import com.advisor.api.iam.domain.auth.vo.RefreshToken
import com.advisor.api.iam.domain.auth.vo.RefreshTokenProps
import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.time.Instant

@Embeddable
class RefreshTokenEmbeddable (
    @Column(name = "refresh_token_token", nullable = false)
    val token: String,

    @Column(name = "refresh_token_jti", nullable = false)
    val jti: String,

    @Column(name = "refresh_token_created_at", nullable = false)
    val createdAt: Instant,

    @Column(name = "refresh_token_expires_at", nullable = false)
    val expiresAt: Instant
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

    fun toDomain(): RefreshToken {
        return RefreshToken.create(
            RefreshTokenProps(
                token = token,
                jti = jti,
                createdAt = createdAt,
                expiresAt = expiresAt
            )
        )
    }
}
