package com.advisor.api.conversation.port.inbound.query

data class GetConversationQuery(
    val id: Long,
    val memberId: Long
)
