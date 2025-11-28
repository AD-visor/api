package com.advisor.api.subscription.adapter.outbound.subscription

import com.advisor.api.common.exception.CustomExceptionCode
import com.advisor.api.common.exception.ErrorStatus

enum class SubscriptionInfraExceptionCode(
    override val status: ErrorStatus,
    override val code: String,
    override val message: String
): CustomExceptionCode {
    SUBSCRIPTION_NOT_FOUND(
        status = ErrorStatus.NOT_FOUND,
        code = "SUBSCRIPTION_NOT_FOUND",
        message = "[Subscription] 해당 구독 정보를 찾을 수 없습니다."
    ),
    SUBSCRIPTION_ALREADY_EXISTS(
        status = ErrorStatus.CONFLICT,
        code = "SUBSCRIPTION_ALREADY_EXISTS",
        message = "[Subscription] 이미 존재하는 구독 정보입니다."
    )
}
