package com.advisor.api.subscription.port.inbound.subscription.listener

import com.advisor.api.common.core.domain.vo.DomainEvent

interface RefreshSubscriptionViewListener {
    fun handle(event: DomainEvent)
}
