package com.advisor.api.conversation.port.outbound.request

import com.advisor.api.ai_prompt_core.model.Prompt

data class AiClientRequest(
    val prompt: Prompt,
    val imageData: ByteArray? = null,
    val temperature: Float? = 0.7f,
    val maxTokens: Int? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AiClientRequest

        if (temperature != other.temperature) return false
        if (maxTokens != other.maxTokens) return false
        if (prompt != other.prompt) return false
        if (imageData?.contentEquals(other.imageData) ?: (other.imageData == null)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = (temperature?.hashCode() ?: 0)
        result = 31 * result + (maxTokens ?: 0)
        result = 31 * result + prompt.hashCode()
        result = 31 * result + (imageData?.contentHashCode() ?: 0)
        return result
    }
}
