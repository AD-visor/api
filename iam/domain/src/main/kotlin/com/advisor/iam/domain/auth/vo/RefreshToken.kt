package com.advisor.iam.domain.auth.vo

import com.advisor.api.common.exception.CustomException
import com.advisor.iam.domain.auth.AuthDomainExceptionCode

data class RefreshToken(val value: String) {
    init { validate() }

    companion object {
        fun create(value: String): RefreshToken {
            return RefreshToken(value)
        }
    }

    private fun validate() {
        require(value.isNotBlank()) { throw CustomException(
            AuthDomainExceptionCode.AUTH_REFRESH_TOKEN_BLANK,
            "[Auth] Refresh Token은 빈 문자열일 수 없습니다."
        ) }
    }
}
