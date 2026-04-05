package com.advisor.api.common.core.domain.event

import com.advisor.api.common.core.domain.vo.DomainEvent

class AiResponseGeneratedEvent(
    val conversationId: Long,
    val conversationMessageId: Long,
    val usedTokens: Long
): DomainEvent() {
    override val eventType: String = "ai.message.created.v1"

    override fun channel(): EventChannel = EventChannel.BOTH
}
