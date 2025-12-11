package com.advisor.api.conversation.port.inbound.listener

import com.advisor.api.common.core.domain.vo.DomainEvent

interface RefreshConversationMessageViewListener {
    fun handle(event: DomainEvent)
}
