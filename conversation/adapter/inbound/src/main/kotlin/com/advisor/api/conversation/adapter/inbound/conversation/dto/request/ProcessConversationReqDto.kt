package com.advisor.api.conversation.adapter.inbound.conversation.dto.request

import com.advisor.api.conversation.port.inbound.command.ProcessConversationCommand

data class ProcessConversationReqDto(
    val userRequest: String,
    val aiMessageId: String?,
) {
    fun toCommand(
        conversationId: Long,
        memberId: Long
    ) = ProcessConversationCommand(
        conversationId = conversationId,
        aiMessageId = aiMessageId?.toLong(),
        memberId = memberId,
        body = userRequest
    )
}
