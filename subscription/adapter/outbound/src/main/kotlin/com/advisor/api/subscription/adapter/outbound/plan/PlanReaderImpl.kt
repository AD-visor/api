package com.advisor.api.subscription.adapter.outbound.plan

import com.advisor.api.common.exception.CustomException
import com.advisor.api.subscription.domain.plan.PlanReader
import com.advisor.api.subscription.domain.plan.PlanView
import org.springframework.stereotype.Repository

@Repository
class PlanReaderImpl(
    private val jpaReader: PlanJpaReader
): PlanReader {
    override fun findAll(): List<PlanView> {
        val entities = jpaReader.findAll()

        return entities.map { it.toModel() }
    }

    override fun findById(id: Long): PlanView {
        val entity = jpaReader.findById(id).orElseThrow { CustomException(
            PlanInfrastructureExceptionCode.PLAN_NOT_FOUND,
            "[PLAN] ${id}에 해당하는 Plan를 찾을 수 없습니다."
        ) }

        return entity.toModel()
    }
}
