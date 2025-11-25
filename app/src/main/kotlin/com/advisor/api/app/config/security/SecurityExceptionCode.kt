package com.advisor.api.app.config.security

import com.advisor.api.common.exception.CustomExceptionCode
import com.advisor.api.common.exception.ErrorStatus

enum class SecurityExceptionCode(
    override val status: ErrorStatus,
    override val code: String,
    override val message: String
): CustomExceptionCode {
    INVALID_ACCESS_TOKEN(
        status = ErrorStatus.UNAUTHORIZED,
        code = "INVALID_ACCESS_TOKEN",
        message = "유효하지 않은 액세스 토큰입니다."
    ),
    INVALID_REFRESH_TOKEN(
        status = ErrorStatus.UNAUTHORIZED,
        code = "INVALID_REFRESH_TOKEN",
        message = "유효하지 않은 리프레시 토큰입니다."
    ),
    MISSING_COOKIE(
        status = ErrorStatus.BAD_REQUEST,
        code = "MISSING_COOKIE",
        message = "쿠키가 존재하지 않습니다."
    ),
    MISSING_TOKEN(
        status = ErrorStatus.BAD_REQUEST,
        code = "MISSING_TOKEN",
        message = "토큰이 존재하지 않습니다."
    );
}
