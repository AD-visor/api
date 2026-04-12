package com.advisor.api.ai_prompt_core.context

data class OutputConstraintContext(
    val language: String = "Korean",
    val toneStyle: String = "PERSUASIVE",
    val format: OutputFormat = OutputFormat.FREE_TEXT,
    val outputSchema: String? = null,
    val strict: Boolean = false
)
