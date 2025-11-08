package com.advisor.api.subscription.domain.subscription.vo

import com.advisor.api.common.exception.CustomException
import com.advisor.api.subscription.domain.subscription.SubscriptionDomainExceptionCode

class MonthlyUsage private constructor(val value: Long) {
    init { validate() }

    companion object {
        fun create(value: Long): MonthlyUsage = MonthlyUsage(value)
    }

    fun add(tokens: Long, planLimit: Long): MonthlyUsage {
        val newValue = value + tokens
        if (newValue > planLimit) throw CustomException(
            SubscriptionDomainExceptionCode.SUBSCRIPTION_INSUFFICIENT_TOKENS,
            "[Subscription] 토큰이 부족합니다."
        )
        return MonthlyUsage(newValue)
    }

    fun reset() = MonthlyUsage(0)

    private fun validate() {
        require(value >= 0) { CustomException(
            SubscriptionDomainExceptionCode.SUBSCRIPTION_MONTHLY_USAGE_NEGATIVE,
            "[MonthlyUsage] 월간 사용량은 음수일 수 없습니다."
        ) }
    }
}