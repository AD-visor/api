package com.advisor.api.conversation.port.inbound.command

data class CreateConversationCommand(
    val memberId: Long,
    val businessType: String,
    val productName: String,
    val description: String,
    val targetAudience: String,
    val toneStyle: String,
    val platform: String
)
