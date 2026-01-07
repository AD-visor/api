package com.advisor.api.ai_prompt_core.context

data class DomainContext(
    val businessType: String,
    val productName: String,
    val examples: List<String>? = emptyList()
)
