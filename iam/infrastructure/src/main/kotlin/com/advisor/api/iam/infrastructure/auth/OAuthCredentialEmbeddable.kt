package com.advisor.api.iam.infrastructure.auth

import com.advisor.api.iam.domain.auth.vo.OAuthCredential
import com.advisor.api.iam.domain.auth.vo.OAuthCredentialProps
import com.advisor.api.iam.domain.auth.vo.OAuthProvider
import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
class OAuthCredentialEmbeddable (
    @Column(name = "oauth_provider", nullable = false)
    val provider: String,

    @Column(name = "oauth_id", nullable = false)
    val oAuthId: String,

    @Column(name = "oauth_access_token")
    val accessToken: String?,
) {
    companion object {
        fun toDomain(oAuthCredentialEmbeddable: OAuthCredentialEmbeddable): OAuthCredential {
            return OAuthCredential.create(OAuthCredentialProps(
                provider = OAuthProvider.fromString(oAuthCredentialEmbeddable.provider),
                oAuthId = oAuthCredentialEmbeddable.oAuthId,
                accessToken = oAuthCredentialEmbeddable.accessToken,
            ))
        }

        fun toPersistence(oAuthCredential: OAuthCredential): OAuthCredentialEmbeddable {
            return OAuthCredentialEmbeddable(
                provider = oAuthCredential.provider.value,
                oAuthId = oAuthCredential.oAuthId,
                accessToken = oAuthCredential.accessToken,
            )
        }
    }
}
