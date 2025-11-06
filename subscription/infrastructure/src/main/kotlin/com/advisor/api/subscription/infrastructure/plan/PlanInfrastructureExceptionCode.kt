package com.advisor.api.subscription.infrastructure.plan

import com.advisor.api.common.exception.CustomExceptionCode
import com.advisor.api.common.exception.ErrorStatus

enum class PlanInfrastructureExceptionCode(
    override val status: ErrorStatus,
    override val code: String,
    override val message: String
): CustomExceptionCode {
    PLAN_NOT_FOUND(
        status = ErrorStatus.NOT_FOUND,
        code = "PLAN_NOT_FOUND",
        message = "[PLAN] 해당 Plan를 찾을 수 없습니다."
    ),
}
