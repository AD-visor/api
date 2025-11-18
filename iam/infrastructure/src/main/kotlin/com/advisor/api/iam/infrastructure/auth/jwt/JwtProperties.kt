package com.advisor.api.iam.infrastructure.auth.jwt

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "jwt")
class JwtProperties(
    val access: TokenProperties,
    val refresh: TokenProperties
) {
    data class TokenProperties(
        val secret: String,
        val expiration: Long
    )
}
