package com.advisor.api.subscription.domain.plan.vo

import com.advisor.api.common.exception.CustomException
import com.advisor.api.subscription.domain.plan.PlanDomainExceptionCode
import com.advisor.api.subscription.domain.subscription.vo.MonthlyUsage

class MonthlyLimit private constructor(val value: Long) {
    init { validate() }

    companion object {
        fun create(value: Long): MonthlyLimit = MonthlyLimit(value)
    }

    fun isExceededBy(usage: MonthlyUsage): Boolean =
        usage.value > value

    fun remainingAfter(usage: MonthlyUsage): Long =
        (value - usage.value).coerceAtLeast(0)

    private fun validate() {
        require(value >= 0) { CustomException(
            PlanDomainExceptionCode.PLAN_MONTHLY_LIMIT_NEGATIVE,
            "[Plan] 월간 한도는 음수일 수 없습니다."
        ) }
    }
}
