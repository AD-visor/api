package com.advisor.api.subscription.domain.plan

import com.advisor.api.common.exception.CustomExceptionCode
import com.advisor.api.common.exception.ErrorStatus

enum class PlanDomainExceptionCode(
    override val status: ErrorStatus,
    override val code: String,
    override val message: String
): CustomExceptionCode {
    PLAN_NAME_EMPTY(
        status = ErrorStatus.BAD_REQUEST,
        code = "PLAN_NAME_EMPTY",
        message = "[PLAN] 이름은 비어 있을 수 없습니다."
    ),
    PLAN_NAME_LENGTH_EXCEEDED(
        status = ErrorStatus.BAD_REQUEST,
        code = "PLAN_NAME_LENGTH_EXCEEDED",
        message = "[PLAN] 이름은 최대 40자 입니다."
    ),
    PLAN_MONTHLY_LIMIT_NEGATIVE(
        status = ErrorStatus.BAD_REQUEST,
        code = "PLAN_MONTHLY_LIMIT_NEGATIVE",
        message = "[PLAN] 월간 한도는 음수일 수 없습니다."
    ),
    PLAN_DESCRIPTION_LENGTH_EXCEEDED(
        status = ErrorStatus.BAD_REQUEST,
        code = "PLAN_DESCRIPTION_LENGTH_EXCEEDED",
        message = "[PLAN] 설명은 최대 255자 입니다."
    ),
}
