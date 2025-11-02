package com.advisor.iam.infrastructure.member

import com.advisor.api.common.exception.CustomExceptionCode
import com.advisor.api.common.exception.ErrorStatus

enum class MemberInfrastructureExceptionCode(
    override val status: ErrorStatus,
    override val code: String,
    override val message: String
): CustomExceptionCode {
    MEMBER_NOT_FOUND(
        status = ErrorStatus.NOT_FOUND,
        code = "MEMBER_NOT_FOUND",
        message = "[Member] 해당 회원이 존재하지 않습니다.")
}
