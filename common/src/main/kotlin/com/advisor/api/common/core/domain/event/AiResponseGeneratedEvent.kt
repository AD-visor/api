package com.advisor.api.common.core.domain.event

import com.advisor.api.common.core.domain.vo.DomainEvent

class AiResponseGeneratedEvent(
    val conversationId: Long,
    val conversationMessageId: Long,
    val memberId: Long,
    val usedTokens: Long
): DomainEvent() {
    override val eventType: String = EventType.AI_RESPONSE_GENERATED.value
    override fun channel(): EventChannel = EventChannel.BOTH
}
