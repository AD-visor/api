package com.advisor.api.conversation.port.inbound.listener

import com.advisor.api.common.core.domain.vo.DomainEvent

interface RefreshConversationViewListener {
    fun handle(event: DomainEvent)
}
