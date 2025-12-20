package com.advisor.api.conversation.adapter.inbound.conversation.dto.response

import com.advisor.api.conversation.port.inbound.result.GetConversationMetadataResult

data class GetConversationMetadataResDto(
    val id: String,
    val productName: String,
    val updatedAt: String
) {
    companion object {
        fun fromResult(result: GetConversationMetadataResult): GetConversationMetadataResDto {
            return GetConversationMetadataResDto(
                id = result.id.toString(),
                productName = result.productName,
                updatedAt = result.updatedAt.toString()
            )
        }
    }
}
