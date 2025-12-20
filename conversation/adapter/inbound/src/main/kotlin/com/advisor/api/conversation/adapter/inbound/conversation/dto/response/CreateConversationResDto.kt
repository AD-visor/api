package com.advisor.api.conversation.adapter.inbound.conversation.dto.response

data class CreateConversationResDto(
    val id: Long
) {
    companion object {
        fun fromResult(id: Long): CreateConversationResDto {
            return CreateConversationResDto(
                id = id
            )
        }
    }
}
