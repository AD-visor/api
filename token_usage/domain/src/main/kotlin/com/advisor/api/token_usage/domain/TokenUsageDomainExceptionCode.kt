package com.advisor.api.token_usage.domain

import com.advisor.api.common.exception.CustomExceptionCode
import com.advisor.api.common.exception.ErrorStatus

enum class TokenUsageDomainExceptionCode(
    override val status: ErrorStatus,
    override val code: String,
    override val message: String
): CustomExceptionCode {
    TOKEN_USAGE_INVALID_SOURCE_CONTEXT(
        status = ErrorStatus.BAD_REQUEST,
        code = "TOKEN_USAGE_INVALID_SOURCE_CONTEXT",
        message = "[TokenUsage] 유효하지 않은 사용처입니다."
    ),
    TOKEN_USAGE_NON_POSITIVE_AMOUNT(
        status = ErrorStatus.BAD_REQUEST,
        code = "TOKEN_USAGE_NON_POSITIVE_AMOUNT",
        message = "[TokenUsage] 토큰 사용량은 양수여야 합니다."
    ),
    TOKEN_USAGE_SOURCE_CONTEXT_MISMATCH(
        status = ErrorStatus.BAD_REQUEST,
        code = "TOKEN_USAGE_SOURCE_CONTEXT_MISMATCH",
        message = "[TokenUsage] 토큰 사용처와 Id type이 일치하지 않습니다."
    ),
    TOKEN_USAGE_INVALID_SOURCE_ID_COMBINATION(
        status = ErrorStatus.BAD_REQUEST,
        code = "TOKEN_USAGE_INVALID_SOURCE_ID_COMBINATION",
        message = "[TokenUsage] 토큰 사용처 Id는 정확히 하나만 존재해야 합니다."
    )
}
