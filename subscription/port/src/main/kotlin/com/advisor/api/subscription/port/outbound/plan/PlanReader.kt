package com.advisor.api.subscription.port.outbound.plan

import com.advisor.api.subscription.port.inbound.plan.view.PlanView

interface PlanReader {
    fun findAll(): List<PlanView>
    fun findById(id: Long): PlanView
    fun refreshView()
}
