package com.advisor.api.iam.domain.member

import com.advisor.api.common.exception.CustomExceptionCode
import com.advisor.api.common.exception.ErrorStatus

enum class MemberDomainExceptionCode(
    override val status: ErrorStatus,
    override val code: String,
    override val message: String
): CustomExceptionCode {
    USER_EMAIL_BLANK(
        status = ErrorStatus.BAD_REQUEST,
        code = "USER_EMAIL_BLANK",
        message = "[Email] 이메일은 필수입니다."
    ),
    USER_INVALID_EMAIL_FORMAT(
        status = ErrorStatus.BAD_REQUEST,
        code = "USER_INVALID_EMAIL_FORMAT",
        message = "[Email] 유효하지 않은 이메일 형식입니다."
    ),
}
