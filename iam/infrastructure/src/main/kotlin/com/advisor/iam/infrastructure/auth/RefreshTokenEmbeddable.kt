package com.advisor.iam.infrastructure.auth

import com.advisor.iam.domain.auth.vo.RefreshToken
import com.advisor.iam.domain.auth.vo.RefreshTokenProps
import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.time.Instant

@Embeddable
class RefreshTokenEmbeddable (
    @Column(nullable = false)
    val token: String,

    @Column(nullable = false)
    val jti: String,

    @Column(nullable = false)
    val createdAt: Instant,

    @Column(nullable = false)
    val expiresAt: Instant
) {
    companion object {
        fun toDomain(refreshTokenEmbeddable: RefreshTokenEmbeddable): RefreshToken {
            return RefreshToken(RefreshTokenProps(
                token = refreshTokenEmbeddable.token,
                jti = refreshTokenEmbeddable.jti,
                createdAt = refreshTokenEmbeddable.createdAt,
                expiresAt = refreshTokenEmbeddable.expiresAt
            ))
        }

        fun toPersistence(refreshToken: RefreshToken): RefreshTokenEmbeddable {
            return RefreshTokenEmbeddable(
                token = refreshToken.token,
                jti = refreshToken.jti,
                createdAt = refreshToken.createdAt,
                expiresAt = refreshToken.expiresAt
            )
        }
    }
}
