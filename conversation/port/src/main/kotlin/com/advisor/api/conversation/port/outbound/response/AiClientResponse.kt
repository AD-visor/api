package com.advisor.api.conversation.port.outbound.response

import com.advisor.api.ai_prompt_core.model.PromptMessage

data class AiClientResponse(
    val message: PromptMessage,
    val usage: Long
)
