package com.advisor.api.conversation.application.prompt.scripts

object OutputConstraintScripts {
    fun language(lang: String) = when(lang.uppercase()) {
        "KOREAN" -> "Language: Use Korean for all responses. However, maintain professional business and technical terminology in English if it's more standard in the industry."
        else -> "Language: Strictly use $lang for all communications."
    }

    fun tone(style: String) = "Tone & Manner: Adopt a $style tone. Ensure the output aligns with the professional standards of a Business Content Strategist."

    fun speech(style: String) = "Speech Style: Finalize all sentences in a $style manner (e.g., using polite or formal sentence endings as appropriate)."
}
