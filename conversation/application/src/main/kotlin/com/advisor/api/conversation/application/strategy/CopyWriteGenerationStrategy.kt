package com.advisor.api.conversation.application.strategy

import com.advisor.api.ai_prompt_core.model.PromptType
import com.advisor.api.conversation.domain.conversation.Conversation
import com.advisor.api.conversation.domain.conversation.entity.ConversationMessageView
import com.advisor.api.conversation.port.inbound.command.GeneratePromptCommand
import com.advisor.api.conversation.port.inbound.prompt.GeneratePromptUseCase
import com.advisor.api.conversation.port.outbound.AiClientPort
import com.advisor.api.conversation.port.outbound.request.AiClientRequest
import org.springframework.stereotype.Component

@Component
class CopyWriteGenerationStrategy(
    private val aiClientPort: AiClientPort,
    private val generatePromptUseCase: GeneratePromptUseCase
) {
    fun generate(
        conversation: Conversation,
        messages: List<ConversationMessageView>,
        userRequest: String
    ): String {
        val prompt = generatePromptUseCase.execute(
            GeneratePromptCommand(
                conversation = conversation,
                messages = messages,
                userRequest = userRequest,
                promptType = PromptType.COPY_WRITING
            )
        )

        return aiClientPort.generateText(AiClientRequest(prompt.prompt)).message.extractTextContent()
    }
}
