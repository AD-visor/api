package com.advisor.iam.infrastructure.auth

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
    )
}
