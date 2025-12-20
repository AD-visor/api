package com.advisor.api.conversation.adapter.inbound.conversation.dto.request

import com.advisor.api.conversation.port.inbound.command.ProcessConversationCommand

data class ProcessConversationReqDto(
    val body: String
) {
    fun toCommand(
        conversationId: Long,
        memberId: Long
    ) = ProcessConversationCommand(
        conversationId = conversationId,
        memberId = memberId,
        body = body
    )
}
