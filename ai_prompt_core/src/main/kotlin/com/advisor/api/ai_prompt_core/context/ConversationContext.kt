package com.advisor.api.ai_prompt_core.context

import com.advisor.api.ai_prompt_core.model.PromptMessage

data class ConversationContext(
    val messages: List<PromptMessage>,
    val maxTokens: Int? = null,
)
