package com.advisor.api.ai_prompt_core.model

data class PromptMessage(
    val role: PromptRole,
    val content: List<MultimodalContent>
) {
    companion object {
        fun system(text: String) =
            PromptMessage(
                PromptRole.SYSTEM,
                listOf(MultimodalContent.Text(text))
            )

        fun user(text: String) =
            PromptMessage(
                PromptRole.USER,
                listOf(MultimodalContent.Text(text))
            )

        fun assistant(text: String) =
            PromptMessage(
                PromptRole.ASSISTANT,
                listOf(MultimodalContent.Text(text))
            )
    }

    fun extractTextContent(): String =
        content.filterIsInstance<MultimodalContent.Text>()
            .joinToString(separator = "\n") { it.value }
}
