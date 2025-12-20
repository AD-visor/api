package com.advisor.api.conversation.application

import com.advisor.api.conversation.domain.conversation.ConversationReader
import com.advisor.api.conversation.port.inbound.GetConversationListUseCase
import com.advisor.api.conversation.port.inbound.query.GetConversationMetadataListQuery
import com.advisor.api.conversation.port.inbound.result.GetConversationMetadataResult
import org.springframework.stereotype.Service

@Service
class GetConversationListService(
    private val conversationReader: ConversationReader
): GetConversationListUseCase {
    override fun execute(query: GetConversationMetadataListQuery): List<GetConversationMetadataResult> {
        val conversations = conversationReader.findAllMetadataByMemberId(query.memberId)

        return conversations.map { GetConversationMetadataResult.fromModel(it) }
    }
}
