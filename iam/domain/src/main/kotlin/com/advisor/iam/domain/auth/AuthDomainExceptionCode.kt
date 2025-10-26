package com.advisor.iam.domain.auth

import com.advisor.api.common.exception.CustomExceptionCode
import com.advisor.api.common.exception.ErrorStatus

enum class AuthDomainExceptionCode(
    override val status: ErrorStatus,
    override val code: String,
    override val message: String
): CustomExceptionCode {
    AUTH_INVALID_OAUTH_PROVIDER(
        status = ErrorStatus.BAD_REQUEST,
        code = "AUTH_INVALID_OAUTH_PROVIDER",
        message = "[Auth] 유효하지 않은 OAuth Provider 입니다."
    ),
    AUTH_REFRESH_TOKEN_BLANK(
        status = ErrorStatus.BAD_REQUEST,
        code = "AUTH_REFRESH_TOKEN_BLANK",
        message = "[Auth] Refresh Token은 필수입니다."
    )
}
