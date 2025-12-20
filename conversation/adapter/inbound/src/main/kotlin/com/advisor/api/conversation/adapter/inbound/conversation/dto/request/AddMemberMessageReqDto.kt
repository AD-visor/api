package com.advisor.api.conversation.adapter.inbound.conversation.dto.request

import com.advisor.api.conversation.port.inbound.command.AddMemberMessageCommand

data class AddMemberMessageReqDto(
    val body: String
) {
    fun toCommand(
        conversationId: Long,
        memberId: Long
    ) = AddMemberMessageCommand(
        conversationId = conversationId,
        memberId = memberId,
        body = body
    )
}
