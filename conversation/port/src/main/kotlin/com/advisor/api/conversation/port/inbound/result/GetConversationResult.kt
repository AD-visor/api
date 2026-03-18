package com.advisor.api.conversation.port.inbound.result

import com.advisor.api.conversation.port.inbound.view.ConversationView
import java.time.Instant

data class GetConversationResult(
    val id: Long,
    val memberId: Long,
    val businessType: String,
    val productName: String,
    val description: String,
    val targetAudience: String,
    val toneStyle: String,
    val speechStyle: String,
    val contentLength: String,
    val platform: String,
    val messages: List<ConversationMessageResult>,
    val createdAt: Instant,
    val updatedAt: Instant
) {
    companion object {
        fun fromModel(model: ConversationView): GetConversationResult {
            return GetConversationResult(
                id = model.id,
                memberId = model.memberId,
                businessType = model.businessType,
                productName = model.productName,
                description = model.description,
                targetAudience = model.targetAudience,
                toneStyle = model.toneStyle,
                speechStyle = model.speechStyle,
                contentLength = model.contentLength,
                platform = model.platform,
                messages = model.messages.map { ConversationMessageResult.fromModel(it) },
                createdAt = model.createdAt,
                updatedAt = model.updatedAt
            )
        }
    }
}
