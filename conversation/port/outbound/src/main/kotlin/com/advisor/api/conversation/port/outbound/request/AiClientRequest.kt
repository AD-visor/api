package com.advisor.api.conversation.port.outbound.request

import com.advisor.api.ai_prompt_core.model.Prompt

data class AiClientRequest(
    val prompt: Prompt,
    val temperature: Float = 0.7f,
    val maxTokens: Int? = null
)
