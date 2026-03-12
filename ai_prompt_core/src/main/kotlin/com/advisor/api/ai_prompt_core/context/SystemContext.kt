package com.advisor.api.ai_prompt_core.context

data class SystemContext(
    val identity: IdentityContext,
    val reasoning: ReasoningContext,
    val outputConstraint: OutputConstraintContext,
    val includeDesignExamples: Boolean = false
)
