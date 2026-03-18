package com.advisor.api.conversation.port.inbound

import com.advisor.api.conversation.port.inbound.query.GetConversationMetadataListQuery
import com.advisor.api.conversation.port.inbound.view.ConversationMetadataView

interface GetConversationListUseCase {
    fun execute(query: GetConversationMetadataListQuery): List<ConversationMetadataView>
}
