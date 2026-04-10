package com.advisor.api.token_usage.adapter.inbound.consumer

import com.advisor.api.common.core.domain.event.EventType
import com.advisor.api.common.core.infrastructure.AbstractKafkaConsumer
import com.advisor.api.common.core.infrastructure.DomainEventHandler
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import tools.jackson.databind.ObjectMapper

@Component
class AiResponseGeneratedEventConsumer(
    private val handlers: List<DomainEventHandler>,
    private val objectMapper: ObjectMapper
): AbstractKafkaConsumer(handlers, objectMapper) {

    override val supportedEventTypes: Set<EventType> = setOf(
        EventType.AI_RESPONSE_GENERATED
    )

    @KafkaListener(
        topics = ["ai.response.generated.v1"]
    )
    fun consume(
        @Payload payload: String,
        @Header(name = "ce_type", required = false) eventTypeName: String?,
        ack: Acknowledgment
    ) {
        process(
            payload = payload,
            eventTypeName = eventTypeName,
            ack = ack
        )
    }
}
