package com.advisor.api.ai_prompt_core.model

sealed interface MultimodalContent {
    data class Text(
        val value: String
    ): MultimodalContent

    data class Image(
        val uri: String,
        val mimeType: String
    ): MultimodalContent
}
