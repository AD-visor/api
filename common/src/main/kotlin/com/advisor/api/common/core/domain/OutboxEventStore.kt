package com.advisor.api.common.core.domain

import com.advisor.api.common.core.domain.vo.DomainEvent

interface OutboxEventStore {
    fun save(event: DomainEvent)
}
