package com.advisor.api.conversation.application

import com.advisor.api.conversation.port.outbound.ConversationReader
import com.advisor.api.conversation.port.inbound.GetConversationListUseCase
import com.advisor.api.conversation.port.inbound.query.GetConversationMetadataListQuery
import com.advisor.api.conversation.port.inbound.view.ConversationMetadataView
import org.springframework.stereotype.Service

@Service
class GetConversationListService(
    private val conversationReader: ConversationReader
): GetConversationListUseCase {
    override fun execute(query: GetConversationMetadataListQuery): List<ConversationMetadataView> {
        return conversationReader.findAllMetadataByMemberId(query.memberId)
    }
}
