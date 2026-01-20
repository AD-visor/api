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
            Please generate a JSON array matching the following Kotlin data structures:
            
            data class TextElement(val text: String, val style: TextStyle)
            data class TextStyle(
                val top: String,          // e.g., "10%" or "100px"
                val left: String,         // e.g., "20%"
                val width: String,        // e.g., "100%"
                val fontSize: String,     // e.g., "40px"
                val fontColor: String,    // Hex color e.g., "#FFFFFF"
                val fontWeight: String,   // "bold" or "normal"
                val textAlign: String,    // "left", "center", "right"
                val textShadow: String?,  // Optional CSS text-shadow
                val backgroundColor: String? // Optional CSS background-color
            )
            
            Constraints:
            - The array can have 1 to 5 elements based on the image context.
            - Position (top/left) should be relative (%) to the image size.
        """.trimIndent()
        else -> null
    }

    fun imageSizeConstraint() = "Image Resolution: Strictly 10240x1024 pixels (1:1 Aspect Ratio)."
}
