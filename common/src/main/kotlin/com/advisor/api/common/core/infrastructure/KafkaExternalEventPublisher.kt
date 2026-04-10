package com.advisor.api.common.core.infrastructure

import com.advisor.api.common.core.domain.OutboxEventStore
import com.advisor.api.common.core.domain.vo.DomainEvent
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Component
class KafkaExternalEventPublisher (
    private val outboxEventStore: OutboxEventStore
) {
    @Transactional(propagation = Propagation.MANDATORY)
    fun publish(event: DomainEvent) {
        outboxEventStore.save(event)
    }
}
