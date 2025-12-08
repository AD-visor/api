package com.advisor.api.subscription.port.inbound.plan.listener

import com.advisor.api.common.core.domain.vo.DomainEvent

interface RefreshPlanViewListener {
    fun handle(event: DomainEvent)
}
