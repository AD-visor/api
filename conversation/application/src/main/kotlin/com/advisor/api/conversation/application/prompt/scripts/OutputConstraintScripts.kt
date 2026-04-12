package com.advisor.api.conversation.application.prompt.scripts

import com.advisor.api.ai_prompt_core.context.OutputFormat
import com.advisor.api.ai_prompt_core.model.PromptType

object OutputConstraintScripts {
    fun language(lang: String) = when(lang.uppercase()) {
        "KOREAN" -> "Language: Use Korean for all responses. However, maintain professional business and technical terminology in English if it's more standard in the industry."
        else -> "Language: Strictly use $lang for all communications."
    }

    fun tone(style: String) = "Tone & Manner: Adopt a $style tone. Ensure the output aligns with the professional standards of a Business Content Strategist."

    fun format(promptType: PromptType) = when (promptType) {
        PromptType.COPY_WRITING -> OutputFormat.FREE_TEXT
        PromptType.INTEGRATED_DESIGN -> OutputFormat.IMAGE
        PromptType.DESIGN_CONCEPT -> OutputFormat.FREE_TEXT
    }

    fun imageGenerationFormat() = """
        FORMAT: Complete finished design (not a background)
        - Include Korean text professionally designed into the image
        - Typography should be a core design element
        - No placeholders or blank spaces for "text to be added later"
        - Think: final deliverable, not work-in-progress
    """.trimIndent()

    fun designCompleteness() = """
        COMPLETENESS CHECK:
        ✓ Korean marketing copy is visible in the image
        ✓ Text is professionally styled (not just plain text)
        ✓ Design looks cohesive and intentional
        ✓ Would be approved by a senior art director
        ✓ Ready to post on Instagram immediately
    """.trimIndent()

    fun schema(promptType: PromptType): String? = when (promptType) {
        else -> null
    }
}
