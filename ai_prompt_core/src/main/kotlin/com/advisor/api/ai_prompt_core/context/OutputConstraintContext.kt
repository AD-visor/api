package com.advisor.api.ai_prompt_core.context

data class OutputConstraintContext(
    val language: String = "Korean",
    val toneStyle: String = "PROFESSIONAL",
    val speechStyle: String = "FORMAL",
    val format: OutputFormat = OutputFormat.FREE_TEXT,
    val schemaDescription: String? = null,
    val strict: Boolean = false
)
