package com.advisor.api.iam.infrastructure.auth

import com.advisor.api.common.core.domain.vo.identifier.AuthId
import com.advisor.api.common.core.domain.vo.identifier.MemberId
import com.advisor.iam.domain.auth.Auth
import com.advisor.iam.domain.auth.AuthProps
import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "auth")
class AuthEntity(
    @Id
    val id: Long,

    @Column
    val memberId: Long,

    @Embedded
    val refreshToken: com.advisor.api.iam.infrastructure.auth.RefreshTokenEmbeddable?,

    @Embedded
    val oAuthCredential: com.advisor.api.iam.infrastructure.auth.OAuthCredentialEmbeddable,

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
        fun toDomain(jpaEntity: com.advisor.api.iam.infrastructure.auth.AuthEntity): Auth {
            val authProps = AuthProps(
                memberId = MemberId(jpaEntity.memberId),
                refreshToken = jpaEntity.refreshToken?.let {
                    com.advisor.api.iam.infrastructure.auth.RefreshTokenEmbeddable.Companion.toDomain(
                        it
                    )
                },
                oAuthCredential = com.advisor.api.iam.infrastructure.auth.OAuthCredentialEmbeddable.Companion.toDomain(
                    jpaEntity.oAuthCredential
                ),
                createdAt = jpaEntity.createdAt,
                updatedAt = jpaEntity.updatedAt,
                isDeleted = jpaEntity.isDeleted,
                deletedAt = jpaEntity.deletedAt
            )

            return Auth.of(AuthId(jpaEntity.id), authProps)
        }

        fun toPersistence(domain: Auth): com.advisor.api.iam.infrastructure.auth.AuthEntity {
            return com.advisor.api.iam.infrastructure.auth.AuthEntity(
                id = domain.id.value,
                memberId = domain.memberId.value,
                refreshToken = domain.refreshToken?.let {
                    com.advisor.api.iam.infrastructure.auth.RefreshTokenEmbeddable.Companion.toPersistence(
                        it
                    )
                },
                oAuthCredential = com.advisor.api.iam.infrastructure.auth.OAuthCredentialEmbeddable.Companion.toPersistence(
                    domain.oAuthCredential
                ),
                createdAt = domain.createdAt,
                updatedAt = domain.updatedAt,
                isDeleted = domain.isDeleted,
                deletedAt = domain.deletedAt
            )
        }
    }
}
