package com.advisor.api.subscription.infrastructure.token_usage

import com.advisor.api.common.exception.CustomExceptionCode
import com.advisor.api.common.exception.ErrorStatus

enum class TokenUsageInfraExceptionCode(
    override val status: ErrorStatus,
    override val code: String,
    override val message: String
): CustomExceptionCode {
    TOKEN_USAGE_NOT_FOUND(
        status = ErrorStatus.NOT_FOUND,
        code = "TOKEN_USAGE_NOT_FOUND",
        message = "[TokenUsage] 해당 토큰 사용 정보를 찾을 수 없습니다."
    ),
}
