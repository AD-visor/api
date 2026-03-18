package com.advisor.api.conversation.port.inbound

import com.advisor.api.conversation.port.inbound.query.GetConversationMetadataListQuery
import com.advisor.api.conversation.port.inbound.result.GetConversationMetadataResult

interface GetConversationListUseCase {
    fun execute(query: GetConversationMetadataListQuery): List<GetConversationMetadataResult>
}
