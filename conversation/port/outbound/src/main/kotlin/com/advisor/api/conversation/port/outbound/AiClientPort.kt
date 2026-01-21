package com.advisor.api.conversation.port.outbound

import com.advisor.api.conversation.port.outbound.request.AiClientRequest
import com.advisor.api.conversation.port.outbound.response.AiClientResponse
import com.advisor.api.conversation.port.outbound.response.AiImageResponse

interface AiClientPort {
    fun generatePrompt(request: AiClientRequest): AiClientResponse
    fun generateImage(request: AiClientRequest): AiImageResponse
}
