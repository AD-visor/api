package com.advisor.api.iam.domain.auth.vo

import com.advisor.api.common.exception.CustomException
import com.advisor.api.iam.domain.auth.AuthDomainExceptionCode
import java.time.Instant

class RefreshToken private constructor(private val props: RefreshTokenProps) {
    init { validate() }

    companion object {
        fun create(props: RefreshTokenProps): RefreshToken {
            return RefreshToken(props)
        }
    }

    private fun validate() {
        require(props.token.isNotBlank()) { throw CustomException(
            AuthDomainExceptionCode.AUTH_REFRESH_TOKEN_BLANK,
            "[Auth] Refresh Token은 빈 문자열일 수 없습니다."
        ) }

        require(props.jti.isNotBlank()) { throw CustomException(
            AuthDomainExceptionCode.AUTH_REFRESH_TOKEN_JTI_BLANK,
            "[Auth] jti는 필수 입니다."
        ) }

        require(props.expiresAt > Instant.now()) { throw CustomException(
            AuthDomainExceptionCode.AUTH_REFRESH_TOKEN_EXPIRED,
            "[Auth] 만료된 토큰 입니다."
        ) }
    }

    val token: String get() = props.token
    val jti: String get() = props.jti
    val createdAt: Instant get() = props.createdAt
    val expiresAt: Instant get() = props.expiresAt
}
