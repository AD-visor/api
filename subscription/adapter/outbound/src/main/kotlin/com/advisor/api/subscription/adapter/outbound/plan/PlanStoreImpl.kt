package com.advisor.api.subscription.adapter.outbound.plan

import com.advisor.api.common.core.domain.vo.identifier.PlanId
import com.advisor.api.common.exception.CustomException
import com.advisor.api.subscription.domain.plan.Plan
import com.advisor.api.subscription.port.outbound.plan.PlanStore
import org.springframework.stereotype.Repository

@Repository
class PlanStoreImpl(
    private val jpaStore: PlanJpaStore
): PlanStore {
    override fun save(plan: Plan) {
        val entity = PlanEntity.fromDomain(plan)
        jpaStore.save(entity)
    }

    override fun loadById(id: PlanId): Plan {
        val entity = jpaStore.findById(id.value).orElseThrow { CustomException(
            PlanInfrastructureExceptionCode.PLAN_NOT_FOUND,
            "[PLAN] ${id}에 해당하는 Plan를 찾을 수 없습니다."
        ) }

        return entity.toDomain()
    }
}
