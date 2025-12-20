package com.advisor.api.iam.application.auth

import com.advisor.api.common.exception.CustomExceptionCode
import com.advisor.api.common.exception.ErrorStatus

enum class AuthApplicationExceptionCode(
    override val status: ErrorStatus,
    override val code: String,
    override val message: String
): CustomExceptionCode {
    AUTH_MISSING_REFRESH_TOKEN(
        status = ErrorStatus.UNAUTHORIZED,
        code = "AUTH_MISSING_REFRESH_TOKEN",
        message = "[Auth] 리프레시 토큰이 존재하지 않습니다."
    ),
    AUTH_INVALID_REFRESH_TOKEN(
        status = ErrorStatus.UNAUTHORIZED,
        code = "AUTH_INVALID_REFRESH_TOKEN",
        message = "[Auth] 리프레시 토큰이 유효하지 않습니다."
    ),
}
