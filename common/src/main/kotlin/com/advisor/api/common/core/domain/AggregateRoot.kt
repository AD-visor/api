package com.advisor.api.common.core.domain

import com.advisor.api.common.core.domain.vo.DomainEvent
import com.advisor.api.common.core.domain.vo.Identifier
import java.io.Serializable

abstract class AggregateRoot<ID : Identifier<out Serializable>>(id: ID): BaseDomainEntity<ID>(id) {
    private val domainEvents = mutableListOf<DomainEvent>()
    val domainEventList: List<DomainEvent> get() = domainEvents

    fun addDomainEvent(event: DomainEvent) {
        domainEvents.add(event)
    }

    fun clearDomainEvents(): List<DomainEvent> {
        val events = domainEvents.toList()
        domainEvents.clear()
        return events
    }
}
