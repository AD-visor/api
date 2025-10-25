package com.advisor.api.common.core.infrastructure

import com.advisor.api.common.core.domain.AggregateRoot
import com.advisor.api.common.core.domain.vo.DomainEvent
import com.advisor.api.common.core.domain.vo.Identifier
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class SpringDomainEventPublisher(
    private val springEventPublisher: ApplicationEventPublisher
) : DomainEventPublisher {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun publish(event: DomainEvent) {
        logger.info("Publishing domain event: ${event::class.simpleName} [id=${event.id}]")
        springEventPublisher.publishEvent(event)
    }

    override fun publishAll(events: List<DomainEvent>) {
        events.forEach { publish(it) }
    }

    override fun <T: Identifier<*>> publishFrom(aggregateRoot: AggregateRoot<T>) {
        val events = aggregateRoot.clearDomainEvents()
        publishAll(events)
    }
}
