package com.advisor.api.conversation.adapter.outbound.prompt

import com.advisor.api.ai_prompt_core.model.MultimodalContent
import com.advisor.api.ai_prompt_core.model.PromptMessage
import com.advisor.api.ai_prompt_core.model.PromptRole
import com.advisor.api.conversation.port.outbound.AiClientPort
import com.advisor.api.conversation.port.outbound.request.AiClientRequest
import com.advisor.api.conversation.port.outbound.response.AiClientResponse
import org.springframework.ai.chat.messages.AssistantMessage
import org.springframework.ai.chat.messages.Message
import org.springframework.ai.chat.messages.SystemMessage
import org.springframework.ai.chat.messages.UserMessage
import org.springframework.ai.chat.model.ChatModel
import org.springframework.ai.chat.prompt.Prompt
import org.springframework.stereotype.Component

@Component
class GeminiAiClientAdapter(
    private val chatModel: ChatModel,
): AiClientPort {
    override fun generatePrompt(request: AiClientRequest): AiClientResponse {
        val messages = request.prompt.messages.map { it.toSpringAiMessage() }
        val prompt = Prompt(messages)
        val chatResponse = chatModel.call(prompt)
        val chatText = chatResponse.result.output.text

        if (chatText.isNullOrEmpty()) {
            throw IllegalStateException("AI model returned empty response")
        }

        val totalUsage = chatResponse.metadata.usage.totalTokens

        val promptMessage = PromptMessage(
            role = PromptRole.ASSISTANT,
            content = listOf(
                MultimodalContent.Text(chatText)
            )
        )

        return AiClientResponse(
            message = promptMessage,
            usage = totalUsage.toLong()
        )
    }

    private fun PromptMessage.toSpringAiMessage(): Message {
        val text = content.filterIsInstance<MultimodalContent.Text>()
            .joinToString("\n") { it.value }

        return when (this.role) {
            PromptRole.USER -> UserMessage(text)
            PromptRole.SYSTEM -> SystemMessage(text)
            PromptRole.ASSISTANT -> AssistantMessage(text)
        }
    }
}
