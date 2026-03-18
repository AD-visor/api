package com.advisor.api.subscription.application.plan.refresher

import com.advisor.api.subscription.port.outbound.plan.PlanReader
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
internal class RefreshPlanViewService(
    private val planReader: PlanReader
) {
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun execute() {
        planReader.refreshView()
    }
}