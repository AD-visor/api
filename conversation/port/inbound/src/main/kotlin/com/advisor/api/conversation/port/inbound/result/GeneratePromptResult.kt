package com.advisor.api.conversation.port.inbound.result

import com.advisor.api.ai_prompt_core.model.Prompt

data class GeneratePromptResult(
    val prompt: Prompt
)
