package com.advisor.api.conversation.port.inbound.command

import com.advisor.api.conversation.domain.conversation.Conversation
import com.advisor.api.conversation.domain.conversation.entity.ConversationMessageView

data class GeneratePromptCommand(
    val conversation: Conversation,
    val messages: List<ConversationMessageView>,
    val userRequest: String,
)
