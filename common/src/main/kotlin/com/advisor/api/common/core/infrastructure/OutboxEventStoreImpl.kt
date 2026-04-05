package com.advisor.api.common.core.infrastructure

import com.advisor.api.common.core.domain.OutboxEventStore
import com.advisor.api.common.core.domain.vo.DomainEvent
import org.springframework.stereotype.Repository
import tools.jackson.core.type.TypeReference
import tools.jackson.databind.json.JsonMapper

@Repository
class OutboxEventStoreImpl(
    private val jpaStore: OutboxEventJpaStore,
    private val objectMapper: JsonMapper
) : OutboxEventStore {
    override fun save(event: DomainEvent) {
        val dataMap = objectMapper.convertValue(event, object : TypeReference<Map<String, *>>() {})

        val entity = OutboxEventEntity(
            id = event.id.value,
            aggregateType = event.aggregateType,
            aggregateId = event.aggregateId,
            eventType = event::class.simpleName
                ?: throw IllegalStateException("익명 클래스는 DomainEvent로 사용할 수 없습니다"),
            payload = dataMap,
            createdAt = event.createdAt
        )

        jpaStore.save(entity)
    }
}
