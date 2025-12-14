package com.advisor.api.conversation.domain.conversation.event

import com.advisor.api.common.core.domain.vo.DomainEvent

class MemberMessageAddedEvent(
    val conversationId: Long,
    val memberId: Long,
    val messageId: Long
): DomainEvent()
