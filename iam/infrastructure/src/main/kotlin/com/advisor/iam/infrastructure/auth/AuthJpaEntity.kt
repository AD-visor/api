package com.advisor.iam.infrastructure.auth

import com.advisor.api.common.core.domain.vo.identifier.AuthId
import com.advisor.api.common.core.domain.vo.identifier.UserId
import com.advisor.iam.domain.auth.Auth
import com.advisor.iam.domain.auth.AuthProps
import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "auth")
class AuthJpaEntity(
    @Id
    val id: String,

    @Column
    val userId: String,

    @Embedded
    val refreshToken: RefreshTokenEmbeddable,

    @Embedded
    val oAuthCredential: OAuthCredentialEmbeddable,

    @Column(nullable = false)
    val createdAt: Instant,

    @Column(nullable = false)
    val updatedAt: Instant,

    @Column(nullable = false)
    val isDeleted: Boolean,

    @Column
    val deletedAt: Instant?
) {
    companion object {
        fun toDomain(jpaEntity: AuthJpaEntity): Auth {
            val authProps = AuthProps(
                userId = UserId(jpaEntity.userId.toLong()),
                refreshToken = RefreshTokenEmbeddable.toDomain(jpaEntity.refreshToken),
                oAuthCredential = OAuthCredentialEmbeddable.toDomain(jpaEntity.oAuthCredential),
                createdAt = jpaEntity.createdAt,
                updatedAt = jpaEntity.updatedAt,
                isDeleted = jpaEntity.isDeleted,
                deletedAt = jpaEntity.deletedAt
            )

            return Auth.of(AuthId(jpaEntity.userId.toLong()), authProps)
        }

        fun toPersistence(domain: Auth): AuthJpaEntity {
            return AuthJpaEntity(
                id = domain.id.value.toString(),
                userId = domain.userId.toString(),
                refreshToken = RefreshTokenEmbeddable.toPersistence(domain.refreshToken),
                oAuthCredential = OAuthCredentialEmbeddable.toPersistence(domain.oAuthCredential),
                createdAt = domain.createdAt,
                updatedAt = domain.updatedAt,
                isDeleted = domain.isDeleted,
                deletedAt = domain.deletedAt
            )
        }
    }
}
