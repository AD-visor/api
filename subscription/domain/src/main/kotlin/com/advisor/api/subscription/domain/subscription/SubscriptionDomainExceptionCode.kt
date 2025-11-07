package com.advisor.api.subscription.domain.subscription

import com.advisor.api.common.exception.CustomExceptionCode
import com.advisor.api.common.exception.ErrorStatus

enum class SubscriptionDomainExceptionCode(
    override val status: ErrorStatus,
    override val code: String,
    override val message: String
): CustomExceptionCode {
    SUBSCRIPTION_INVALID_STATUS(
        status = ErrorStatus.BAD_REQUEST,
        code = "SUBSCRIPTION_INVALID_STATUS",
        message = "[Subscription] 유효하지 않은 구독 상태입니다."
    ),
    SUBSCRIPTION_MONTHLY_USAGE_EXCEEDED(
        status = ErrorStatus.BAD_REQUEST,
        code = "SUBSCRIPTION_MONTHLY_USAGE_EXCEEDED",
        message = "[Subscription] 월간 토큰 사용 한도를 초과하였습니다."
    ),
    SUBSCRIPTION_INSUFFICIENT_TOKENS(
        status = ErrorStatus.BAD_REQUEST,
        code = "SUBSCRIPTION_INSUFFICIENT_TOKENS",
        message = "[Subscription] 구독 토큰이 부족합니다."
    ),
    SUBSCRIPTION_MONTHLY_USAGE_NEGATIVE(
        status = ErrorStatus.BAD_REQUEST,
        code = "SUBSCRIPTION_MONTHLY_USAGE_NEGATIVE",
        message = "[Subscription] 월간 토큰 사용량은 음수일 수 없습니다."
    )
}
