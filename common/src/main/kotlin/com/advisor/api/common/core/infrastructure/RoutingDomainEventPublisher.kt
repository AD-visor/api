package com.advisor.api.common.core.infrastructure

import com.advisor.api.common.core.domain.AggregateRoot
import com.advisor.api.common.core.domain.event.EventChannel
import com.advisor.api.common.core.domain.vo.DomainEvent
import com.advisor.api.common.core.domain.vo.Identifier
import mu.KotlinLogging
import org.springframework.stereotype.Component

@Component
class RoutingDomainEventPublisher(
    private val internalPublisher: SpringInternalEventPublisher,
    private val externalPublisher: KafkaExternalEventPublisher
) : DomainEventPublisher {

    private val logger = KotlinLogging.logger {}

    override fun publish(event: DomainEvent) {
        logger.info("Routing event: ${event::class.simpleName} [id=${event.id}]")

        when (event.channel()) {
            EventChannel.INTERNAL -> {
                internalPublisher.publish(event)
            }

            EventChannel.KAFKA -> {
                externalPublisher.publish(event)
            }

            EventChannel.BOTH -> {
                internalPublisher.publish(event)
                externalPublisher.publish(event)
            }
        }
    }

    override fun publishAll(events: List<DomainEvent>) {
        events.forEach { publish(it) }
    }

    override fun <T : Identifier<*>> publishFrom(aggregateRoot: AggregateRoot<T>) {
        val events = aggregateRoot.clearDomainEvents()
        publishAll(events)
    }
}
