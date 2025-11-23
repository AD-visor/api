package com.advisor.api.iam.adapter.outbound.auth

import com.advisor.api.common.exception.CustomExceptionCode
import com.advisor.api.common.exception.ErrorStatus

enum class AuthInfrastructureExceptionCode(
    override val status: ErrorStatus,
    override val code: String,
    override val message: String
): CustomExceptionCode {
    AUTH_NOT_FOUND(
        status = ErrorStatus.NOT_FOUND,
        code = "AUTH_NOT_FOUND",
        message = "[Auth] 해당 유저의 인증 정보가 존재하지 않습니다."
    ),
    AUTH_REFRESH_TOKEN_PERSISTENCE_ERROR(
        status = ErrorStatus.INTERNAL_ERROR,
        code = "AUTH_REFRESH_TOKEN_PERSISTENCE_ERROR",
        message = "[Auth] RefreshToken 데이터가 불완전합니다."
    ),
    AUTH_TOKEN_EXPIRED(
        status = ErrorStatus.UNAUTHORIZED,
        code = "AUTH_TOKEN_EXPIRED",
        message = "[Auth] 토큰이 만료되었습니다."
    ),
    AUTH_MALFORMED_TOKEN(
        status = ErrorStatus.UNAUTHORIZED,
        code = "AUTH_MALFORMED_TOKEN",
        message = "[Auth] 지원하지 않는 토큰 형식입니다."
    ),
    AUTH_TOKEN_INVALID_SIGNATURE(
        status = ErrorStatus.UNAUTHORIZED,
        code = "AUTH_TOKEN_INVALID_SIGNATURE",
        message = "[Auth] 토큰 서명이 유효하지 않습니다."
    ),
    AUTH_TOKEN_EMPTY(
        status = ErrorStatus.BAD_REQUEST,
        code = "AUTH_TOKEN_EMPTY",
        message = "[Auth] 토큰이 비어있습니다."
    ),
    AUTH_UNEXPECTED_TOKEN_ERROR(
        status = ErrorStatus.INTERNAL_ERROR,
        code = "AUTH_UNEXPECTED_TOKEN_ERROR",
        message = "[Auth] 토큰 처리 중 알 수 없는 오류가 발생했습니다."
    )
}
