package com.advisor.api.common.core.infrastructure

import com.advisor.api.common.core.domain.vo.DomainEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class SpringInternalEventPublisher(
    private val publisher: ApplicationEventPublisher
) {
    fun publish(event: DomainEvent) {
        publisher.publishEvent(event)
    }
}
