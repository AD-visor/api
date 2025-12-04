package com.advisor.api.subscription.domain.plan

interface PlanReader {
    fun findAll(): List<PlanView>
    fun findById(id: Long): PlanView
    fun refreshView()
}
