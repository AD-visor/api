package com.advisor.api.conversation.port.outbound

import com.advisor.api.conversation.port.outbound.request.AiClientRequest
import com.advisor.api.conversation.port.outbound.response.AiClientResponse
import com.advisor.api.conversation.port.outbound.response.AiImageResponse

interface AiClientPort {
    suspend fun generateText(request: AiClientRequest): AiClientResponse
    suspend fun generateImage(request: AiClientRequest): AiImageResponse
}
