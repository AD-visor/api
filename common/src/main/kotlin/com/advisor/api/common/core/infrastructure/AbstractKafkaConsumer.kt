package com.advisor.api.common.core.infrastructure

import com.advisor.api.common.core.domain.event.EventType
import mu.KotlinLogging
import org.springframework.kafka.support.Acknowledgment
import tools.jackson.databind.ObjectMapper

abstract class AbstractKafkaConsumer (
    private val handlers: List<DomainEventHandler>,
    private val objectMapper: ObjectMapper
) {
    val logger = KotlinLogging.logger {}

    abstract val supportedEventTypes: Set<EventType>

    private val handlerMap: Map<EventType, DomainEventHandler> by lazy {
        supportedEventTypes.associateWith { eventType ->
            handlers.find { it.supports(eventType) }
        }.filterValues { it != null }
            .mapValues { it.value!! }
            .also { map ->
                val unhandled = supportedEventTypes - map.keys
                if (unhandled.isNotEmpty()) {
                    logger.warn { "핸들러 미등록 EventType: $unhandled" }
                }
            }
    }

    protected fun process(
        payload: String,
        eventTypeName: String?,
        ack: Acknowledgment
    ) {
        runCatching {
            val eventType = resolveEventType(eventTypeName, payload)

            if (eventType == null) {
                logger.warn { "알 수 없는 eventType, skip: eventTypeName=$eventTypeName, payload=${payload.take(200)}" }
                ack.acknowledge()
                return
            }

            val handler = handlerMap[eventType]

            if (handler == null) {
                logger.warn { "처리할 핸들러 없음, skip: eventType=$eventType" }
                ack.acknowledge()
                return
            }

            handler.handle(payload)
            ack.acknowledge()
            logger.debug { "이벤트 처리 완료: eventType=$eventType" }

        }.onFailure { e ->
            logger.error(e) { "이벤트 처리 실패: payload=${payload.take(200)}" }
            throw e
        }
    }

    private fun resolveEventType(eventTypeName: String?, payload: String): EventType? {
        val name = eventTypeName
            ?: runCatching {
                objectMapper.readTree(payload).get("eventType")?.asText()
            }.getOrNull()
            ?: return null

        return EventType.fromValue(name).takeIf { it != EventType.UNKNOWN }
    }
}
