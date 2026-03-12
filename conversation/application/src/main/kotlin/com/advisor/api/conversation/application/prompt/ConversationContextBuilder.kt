package com.advisor.api.conversation.application.prompt

import com.advisor.api.ai_prompt_core.context.ConversationContext
import com.advisor.api.ai_prompt_core.model.MultimodalContent
import com.advisor.api.ai_prompt_core.model.PromptMessage
import com.advisor.api.ai_prompt_core.model.PromptRole
import com.advisor.api.conversation.domain.conversation.entity.ConversationMessageView
import org.springframework.stereotype.Component

@Component
class ConversationContextBuilder {

    fun build(messages: List<ConversationMessageView>): ConversationContext {
        return ConversationContext(
            messages = messages.map { message ->
                val role = when (message.role) {
                    "MEMBER" -> PromptRole.USER
                    "ASSISTANT" -> PromptRole.ASSISTANT
                    else -> PromptRole.USER
                }

                PromptMessage(
                    content = listOf(MultimodalContent.Text(message.body)),
                    role = role
                )
            }
        )
    }
}
