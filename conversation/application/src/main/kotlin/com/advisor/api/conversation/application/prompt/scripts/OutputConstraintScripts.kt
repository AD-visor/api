package com.advisor.api.conversation.application.prompt.scripts

import com.advisor.api.ai_prompt_core.context.OutputFormat
import com.advisor.api.ai_prompt_core.model.PromptType

object OutputConstraintScripts {
    fun language(lang: String) = when(lang.uppercase()) {
        "KOREAN" -> "Language: Use Korean for all responses. However, maintain professional business and technical terminology in English if it's more standard in the industry."
        else -> "Language: Strictly use $lang for all communications."
    }

    fun tone(style: String) = "Tone & Manner: Adopt a $style tone. Ensure the output aligns with the professional standards of a Business Content Strategist."

    fun speech(style: String) = "Speech Style: Finalize all sentences in a $style manner (e.g., using polite or formal sentence endings as appropriate)."

    fun format(promptType: PromptType) = when (promptType) {
        PromptType.LAYOUT_ANALYSIS -> OutputFormat.JSON
        else -> OutputFormat.FREE_TEXT
    }

    fun schema(promptType: PromptType): String? = when (promptType) {
        PromptType.LAYOUT_ANALYSIS -> """
            {
              "headline": { "text": "string", "x": 0-100, "y": 0-100, "fontSize": "number", "color": "hex" },
              "subtext": { "text": "string", "x": 0-100, "y": 0-100, "fontSize": "number", "color": "hex" },
              "cta": { "text": "string", "x": 0-100, "y": 0-100, "fontSize": "number", "color": "hex" },
              "themeColor": "hex string",
              "backgroundType": "Dark | Light | Vibrant"
            }
        """.trimIndent()
        else -> null
    }

    fun imageSizeConstraint() = "Image Resolution: Strictly 1080x1080 pixels (1:1 Aspect Ratio)."
}
