package com.advisor.api.ai_prompt_core.context

data class UserContext(
    val task: TaskContext?,
    val domain: DomainContext?,
    val conversation: ConversationContext?,
    val designBrief: DesignBriefContext? = null
)
