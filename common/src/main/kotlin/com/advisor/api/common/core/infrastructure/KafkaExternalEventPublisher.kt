package com.advisor.api.common.core.infrastructure

import com.advisor.api.common.core.domain.OutboxEventStore
import com.advisor.api.common.core.domain.vo.DomainEvent
import org.springframework.stereotype.Component

@Component
class KafkaExternalEventPublisher (
    private val outboxEventStore: OutboxEventStore
) {
    fun publish(event: DomainEvent) {
        outboxEventStore.save(event)
    }
}
