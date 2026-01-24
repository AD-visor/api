package com.advisor.api.conversation.adapter.outbound.prompt

import com.advisor.api.ai_prompt_core.model.MultimodalContent
import com.advisor.api.ai_prompt_core.model.PromptMessage
import com.advisor.api.ai_prompt_core.model.PromptRole
import com.advisor.api.common.exception.CustomException
import com.advisor.api.conversation.port.outbound.AiClientPort
import com.advisor.api.conversation.port.outbound.request.AiClientRequest
import com.advisor.api.conversation.port.outbound.response.AiClientResponse
import com.advisor.api.conversation.port.outbound.response.AiImageResponse
import com.advisor.api.media.port.outbound.ImageFetchPort
import com.advisor.api.media.port.outbound.command.ImageFetchCommand
import org.springframework.ai.chat.messages.AssistantMessage
import org.springframework.ai.chat.messages.Message
import org.springframework.ai.chat.messages.SystemMessage
import org.springframework.ai.chat.messages.UserMessage
import org.springframework.ai.chat.model.ChatModel
import org.springframework.ai.chat.prompt.Prompt
import org.springframework.ai.image.ImageModel
import org.springframework.ai.image.ImageOptionsBuilder
import org.springframework.ai.image.ImagePrompt
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient

@Component
class GeminiAiClientAdapter(
    private val imageFetchPot: ImageFetchPort,
    @Qualifier("googleGenAiChatModel") private val chatModel: ChatModel,
    private val imageModel: ImageModel,
    private val restClient: RestClient
): AiClientPort {
    override fun generateText(request: AiClientRequest): AiClientResponse {
        val messages = request.prompt.messages.map { it.toSpringAiMessage() }
        val prompt = Prompt(messages)
        val chatResponse = chatModel.call(prompt)
        val chatText = chatResponse.result.output.text

        if (chatText.isNullOrEmpty()) { throw CustomException(
            PromptAdapterExceptionCode.PROMPT_EMPTY_RESPONSE,
            "[Prompt] 채팅 모델 응답이 비어 있습니다."
        ) }

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

    override fun generateImage(request: AiClientRequest): AiImageResponse {
        // 1. 이미지 생성 옵션 설정 (크기, 품질 등)
        val options = ImageOptionsBuilder.builder()
            .height(1024)
            .width(1024)
            .N(1) // 생성할 이미지 개수
            .build()

        // 2. 모델 호출
        val promptText = request.prompt.messages
            .joinToString("\n") { message ->
                message.content.filterIsInstance<MultimodalContent.Text>()
                    .joinToString(" ") { it.value }
            }
        val imagePrompt = ImagePrompt(promptText, options)
        val imageResponse = imageModel.call(imagePrompt)

        // 3. 결과 URL 추출
        val imageUrl = imageResponse.result.output.url
            ?: throw CustomException(
                PromptAdapterExceptionCode.PROMPT_EMPTY_RESPONSE,
                "[Prompt] 이미지 생성 모델 응답이 비어 있습니다."
            )

        // 4. URL로부터 바이너리 데이터(ByteArray) 다운로드
        val imageData = imageFetchPot.fetch(ImageFetchCommand.Fetch(imageUrl)).image
        val count = imageResponse.results.size

        return AiImageResponse(imageData, count)
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
