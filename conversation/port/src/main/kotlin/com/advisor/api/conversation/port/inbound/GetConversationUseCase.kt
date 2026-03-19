package com.advisor.api.conversation.port.inbound

import com.advisor.api.conversation.port.inbound.query.GetConversationQuery
import com.advisor.api.conversation.port.inbound.view.ConversationView

interface GetConversationUseCase {
    fun execute(query: GetConversationQuery): ConversationView
}
