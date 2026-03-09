package com.advisor.api.conversation.application.strategy

import com.advisor.api.ai_prompt_core.model.PromptType
import com.advisor.api.conversation.domain.conversation.Conversation
import com.advisor.api.conversation.domain.conversation.entity.ConversationMessageView
import com.advisor.api.conversation.port.inbound.command.GeneratePromptCommand
import com.advisor.api.conversation.port.inbound.prompt.GeneratePromptUseCase
import com.advisor.api.conversation.port.outbound.AiClientPort
import com.advisor.api.conversation.port.outbound.request.AiClientRequest
import com.advisor.api.conversation.port.outbound.response.AiImageResponse
import org.springframework.stereotype.Component

@Component
class ImageGenerationStrategy(
    private val aiClientPort: AiClientPort,
    private val generatePromptUseCase: GeneratePromptUseCase
) {
    suspend fun generate(
        conversation: Conversation,
        messages: List<ConversationMessageView>,
        copyWrite: String
    ): AiImageResponse {
        val input = "Based on this copy: '$copyWrite', generate a background image prompt."
        val prompt = generatePromptUseCase.execute(
            GeneratePromptCommand(
                conversation,
                messages,
                input,
                PromptType.IMAGE_GENERATION
            )
        )

        return aiClientPort.generateImage(AiClientRequest(prompt.prompt))
    }
}
