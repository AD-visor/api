package com.advisor.api.iam.adapter.outbound.auth

import com.advisor.api.common.core.domain.vo.identifier.AuthId
import com.advisor.api.common.core.domain.vo.identifier.MemberId
import com.advisor.api.iam.domain.auth.Auth
import com.advisor.api.iam.domain.auth.AuthProps
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
    val refreshToken: RefreshTokenEmbeddable?,

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
        fun toPersistence(domain: Auth): AuthEntity {
            return AuthEntity(
                id = domain.id.value,
                memberId = domain.memberId.value,
                refreshToken = domain.refreshToken?.let {
                    RefreshTokenEmbeddable.toPersistence(
                        it
                    )
                },
                oAuthCredential = OAuthCredentialEmbeddable.toPersistence(
                    domain.oAuthCredential
                ),
                createdAt = domain.createdAt,
                updatedAt = domain.updatedAt,
                isDeleted = domain.isDeleted,
                deletedAt = domain.deletedAt
            )
        }
    }

    fun toDomain(): Auth {
        val authProps = AuthProps(
            memberId = MemberId(memberId),
            refreshToken = refreshToken?.toDomain(),
            oAuthCredential = oAuthCredential.toDomain(),
            createdAt = createdAt,
            updatedAt = updatedAt,
            isDeleted = isDeleted,
            deletedAt = deletedAt
        )

        return Auth.of(AuthId(id), authProps)
    }
}
