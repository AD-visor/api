package com.advisor.api.ai_prompt_core.context

data class IdentityContext(
    val instruction: String,
    val principles: List<String>,
    val constraints: List<String>
)
