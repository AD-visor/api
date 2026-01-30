package com.advisor.api.conversation.port.inbound.result

import com.advisor.api.conversation.domain.conversation.entity.ConversationMessageView
import java.time.Instant

data class ConversationMessageResult(
    val id: String,
    val conversationId: String,
    val role: String,
    val body: String,
    val revisionOf: Long?,
    val parentMessageId: Long? = null,
    val createdAt: Instant
) {
    companion object {
        fun fromModel(model: ConversationMessageView): ConversationMessageResult {
            return ConversationMessageResult(
                id = model.id.toString(),
                conversationId = model.conversationId.toString(),
                role = model.role,
                body = model.body,
                revisionOf = model.revisionOf,
                parentMessageId = model.parentMessageId,
                createdAt = model.createdAt
            )
        }
    }
}
