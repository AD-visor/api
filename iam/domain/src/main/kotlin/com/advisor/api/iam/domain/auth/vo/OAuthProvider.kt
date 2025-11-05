package com.advisor.api.iam.domain.auth.vo

import com.advisor.api.common.exception.CustomException
import com.advisor.api.iam.domain.auth.AuthDomainExceptionCode

enum class OAuthProvider(val value: String) {
    KAKAO("KAKAO"),
    GOOGLE("GOOGLE"),
    NAVER("NAVER");

    companion object {
        fun fromString(value: String): OAuthProvider =
            entries.find { it.value.equals(value, ignoreCase = true) }
                ?: throw CustomException(
                    AuthDomainExceptionCode.AUTH_INVALID_OAUTH_PROVIDER,
                    "[Auth] 유효하지 않은 OAuth Provider 입니다."
                )
    }
}
