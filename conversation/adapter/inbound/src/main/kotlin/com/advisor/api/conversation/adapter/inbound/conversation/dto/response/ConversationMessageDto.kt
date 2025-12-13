package com.advisor.api.conversation.adapter.inbound.conversation.dto.response

import com.advisor.api.conversation.port.inbound.result.ConversationMessageResult
import java.time.Instant

data class ConversationMessageDto(
    val id: String,
    val conversationId: String,
    val role: String,
    val body: String,
    val revisionOf: String?,
    val createdAt: Instant
) {
    companion object {
        fun fromResult(result: ConversationMessageResult): ConversationMessageDto {
            return ConversationMessageDto(
                id = result.id,
                conversationId = result.conversationId,
                role = result.role,
                body = result.body,
                revisionOf = result.revisionOf.toString(),
                createdAt = result.createdAt
            )
        }
    }
}
