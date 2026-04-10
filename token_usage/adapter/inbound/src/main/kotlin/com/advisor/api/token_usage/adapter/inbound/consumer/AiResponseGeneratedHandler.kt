package com.advisor.api.token_usage.adapter.inbound.consumer

import com.advisor.api.common.core.domain.event.EventType
import com.advisor.api.common.core.infrastructure.DomainEventHandler
import com.advisor.api.subscription.port.inbound.subscription.GetActiveSubscriptionByMemberIdUseCase
import com.advisor.api.subscription.port.inbound.subscription.query.GetActiveSubscriptionByMemberIdQuery
import com.advisor.api.token_usage.adapter.inbound.consumer.payload.AiResponseGeneratedPayload
import com.advisor.api.token_usage.port.inbound.RecordTokenUsageUseCase
import com.advisor.api.token_usage.port.inbound.command.RecordTokenUsageCommand
import org.springframework.stereotype.Component
import tools.jackson.databind.json.JsonMapper
import tools.jackson.module.kotlin.readValue

@Component
class AiResponseGeneratedHandler(
    private val recordTokenUsageUseCase: RecordTokenUsageUseCase,
    private val getActiveSubscriptionByMemberIdUseCase: GetActiveSubscriptionByMemberIdUseCase,
    private val objectMapper: JsonMapper
) : DomainEventHandler {

    override fun supports(eventType: EventType) =
        eventType == EventType.AI_RESPONSE_GENERATED

    override fun handle(payload: String) {
        val data = objectMapper.readValue<AiResponseGeneratedPayload>(payload)

        val subscription = getActiveSubscriptionByMemberIdUseCase.execute(
            GetActiveSubscriptionByMemberIdQuery(memberId = data.memberId)
        )

        recordTokenUsageUseCase.execute(
            RecordTokenUsageCommand(
                memberId = data.memberId,
                subscriptionId = subscription.id,
                planId = subscription.planId,
                usedTokens = data.usedTokens,
                conversationMessageId = data.conversationMessageId
            )
        )
    }
}
