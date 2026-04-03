package com.advisor.api.common.core.domain

import com.advisor.api.common.core.domain.vo.DomainEvent
import com.advisor.api.common.core.domain.vo.Identifier
import java.io.Serializable

abstract class AggregateRoot<ID : Identifier<out Serializable>>(id: ID): BaseDomainEntity<ID>(id) {
    private val domainEvents = mutableListOf<DomainEvent>()
    val domainEventList: List<DomainEvent> get() = domainEvents

    fun addDomainEvent(event: DomainEvent) {
        event.aggregateType = this::class.simpleName
            ?: throw IllegalStateException("익명 클래스는 AggregateRoot로 사용할 수 없습니다")
        event.aggregateId = id.value.toString()

        domainEvents.add(event)
    }

    fun clearDomainEvents(): List<DomainEvent> {
        val events = domainEvents.toList()
        domainEvents.clear()
        return events
    }
}
