package com.advisor.api.conversation.adapter.inbound.conversation.dto.response

import com.advisor.api.conversation.port.inbound.result.GetConversationResult
import java.time.Instant

data class GetConversationResDto(
    val id: String,
    val memberId: String,
    val businessType: String,
    val productName: String,
    val description: String,
    val targetAudience: String,
    val toneStyle: String,
    val speechStyle: String,
    val contentLength: String,
    val platform: String,
    val messages: List<ConversationMessageDto>,
    val createdAt: Instant
) {
    companion object {
        fun fromResult(result: GetConversationResult): GetConversationResDto {
            return GetConversationResDto(
                id = result.id.toString(),
                memberId = result.memberId.toString(),
                businessType = result.businessType,
                productName = result.productName,
                description = result.description,
                targetAudience = result.targetAudience,
                toneStyle = result.toneStyle,
                speechStyle = result.speechStyle,
                contentLength = result.contentLength,
                platform = result.platform,
                messages = result.messages.map { ConversationMessageDto.fromResult(it) },
                createdAt = result.createdAt
            )
        }
    }
}
