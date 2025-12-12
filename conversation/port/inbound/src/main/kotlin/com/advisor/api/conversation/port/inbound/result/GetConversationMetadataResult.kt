package com.advisor.api.conversation.port.inbound.result

import com.advisor.api.conversation.domain.conversation.ConversationMetadataView
import java.time.Instant

data class GetConversationMetadataResult(
    val id: Long,
    val productName: String,
    val updatedAt: Instant
) {
    companion object {
        fun fromModel(view: ConversationMetadataView): GetConversationMetadataResult {
            return GetConversationMetadataResult(
                id = view.id,
                productName = view.productName,
                updatedAt = view.updatedAt
            )
        }
    }
}
