package com.advisor.api.common.core.infrastructure

import com.advisor.api.common.core.domain.AggregateRoot
import com.advisor.api.common.core.domain.vo.DomainEvent
import com.advisor.api.common.core.domain.vo.Identifier

interface DomainEventPublisher {
    fun publish(event: DomainEvent)
    fun publishAll(events: List<DomainEvent>)
    fun <T: Identifier<*>> publishFrom(aggregateRoot: AggregateRoot<T>)
}
