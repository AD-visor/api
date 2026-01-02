package com.advisor.api.ai_prompt_core.context

data class TaskContext(
    val objective: String,
    val description: String,
    val requirements: List<String> = emptyList(),
    val steps: List<String> = emptyList()
)
