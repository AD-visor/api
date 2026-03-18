package com.advisor.api.conversation.application

import com.advisor.api.conversation.port.outbound.ConversationReader
import com.advisor.api.conversation.port.inbound.GetConversationUseCase
import com.advisor.api.conversation.port.inbound.query.GetConversationQuery
import com.advisor.api.conversation.port.inbound.result.GetConversationResult
import org.springframework.stereotype.Service

@Service
class GetConversationService(
    private val conversationReader: ConversationReader
): GetConversationUseCase {
    override fun execute(query: GetConversationQuery): GetConversationResult {
        val conversation = conversationReader.findByIdAndMemberId(
            id = query.id,
            memberId = query.memberId
        )

        return GetConversationResult.fromModel(conversation)
    }
}
