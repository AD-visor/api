package com.advisor.api.common.core.domain

import com.advisor.api.common.core.domain.vo.Identifier
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class DomainEventPublisher(
    private val eventPublisher: ApplicationEventPublisher
) {
    fun <T : Identifier<Long>> publish(aggregateRoot: AggregateRoot<T>) {
        val domainEvents = aggregateRoot.clearDomainEvents()
        domainEvents.forEach { eventPublisher.publishEvent(it) }
    }
}
