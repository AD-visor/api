package com.advisor.api.subscription.application.plan

import com.advisor.api.subscription.domain.plan.PlanReader
import com.advisor.api.subscription.port.inbound.plan.RefreshPlanViewUseCase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
class RefreshPlanViewService(
    private val planReader: PlanReader
): RefreshPlanViewUseCase {
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    override fun execute() {
        planReader.refreshView()
    }
}
