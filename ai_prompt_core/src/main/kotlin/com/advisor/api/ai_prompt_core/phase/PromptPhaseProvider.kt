package com.advisor.api.ai_prompt_core.phase

import com.advisor.api.ai_prompt_core.model.PromptMessage

interface PromptPhaseProvider<C> {
    val phase: PromptPhase
    fun provide(context: C): List<PromptMessage>
}
