package com.advisor.api.common.core.infrastructure

import com.advisor.api.common.core.domain.event.EventType

interface DomainEventHandler {
    fun supports(eventType: EventType): Boolean
    fun handle(payload: String)
}
