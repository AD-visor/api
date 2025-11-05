package com.advisor.api.iam.domain.auth.vo

class OAuthCredential private constructor(private val props: OAuthCredentialProps) {
    init { validate() }

    companion object {
        fun create(props: OAuthCredentialProps): OAuthCredential {
            return OAuthCredential(props)
        }
    }

    private fun validate() {}

    val provider: OAuthProvider get() = props.provider
    val oAuthId: String get() = props.oAuthId
    val accessToken: String? get() = props.accessToken
}
