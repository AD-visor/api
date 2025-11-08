package com.advisor.api.subscription.domain.plan

import com.advisor.api.common.core.domain.AggregateRoot
import com.advisor.api.common.core.domain.vo.Money
import com.advisor.api.common.core.domain.vo.identifier.PlanId
import com.advisor.api.common.exception.CustomException
import com.advisor.api.subscription.domain.plan.vo.MonthlyLimit
import java.time.Instant

class Plan private constructor(
    id: PlanId,
    private val props: PlanProps,
): AggregateRoot<PlanId>(id) {
    init { validate() }

    companion object {
        fun create(id: PlanId, props: PlanProps): Plan {
            return Plan(id, props)
        }

        fun of(id: PlanId, props: PlanProps): Plan {
            return Plan(id, props)
        }
    }

    private fun validate() {
        require(props.name.isNotBlank()) { CustomException(
            PlanDomainExceptionCode.PLAN_NAME_EMPTY,
            "[PLAN] 이름은 공백일 수 없습니다."
        ) }
        require(props.name.length <= 40) { CustomException(
            PlanDomainExceptionCode.PLAN_NAME_LENGTH_EXCEEDED,
            "[PLAN] 이름은 최대 40자 입니다."
        ) }

        require((props.description?.length ?: 0) < 256) { CustomException(
            PlanDomainExceptionCode.PLAN_DESCRIPTION_LENGTH_EXCEEDED,
            "[PLAN] 설명은 최대 255자 입니다."
        ) }
    }

    val name: String get() = props.name
    val monthlyLimit: MonthlyLimit get() = props.monthlyLimit
    val price: Money get() = props.price
    val description: String? get() = props.description
    val createdAt: Instant get() = props.createdAt
    val updatedAt: Instant get() = props.updatedAt
    val isDeleted: Boolean get() = props.isDeleted
    val deletedAt: Instant? get() = props.deletedAt
}
