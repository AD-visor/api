package com.advisor.api.conversation.application.strategy

import com.advisor.api.ai_prompt_core.model.PromptType
import com.advisor.api.common.exception.CustomException
import com.advisor.api.conversation.application.ConversationApplicationExceptionCode
import com.advisor.api.conversation.domain.conversation.Conversation
import com.advisor.api.conversation.port.inbound.command.GeneratePromptCommand
import com.advisor.api.conversation.port.inbound.prompt.GeneratePromptUseCase
import com.advisor.api.conversation.port.outbound.AiClientPort
import com.advisor.api.conversation.port.outbound.request.AiClientRequest
import com.advisor.api.media.port.outbound.command.ImageEditCommand
import org.springframework.stereotype.Component
import tools.jackson.core.type.TypeReference
import tools.jackson.databind.ObjectMapper

@Component
class LayoutAnalysisStrategy(
    private val generatePromptUseCase: GeneratePromptUseCase,
    private val aiClientPort: AiClientPort,
    private val objectMapper: ObjectMapper
) {
    suspend fun analyze(
        conversation: Conversation,
        copyWrite: String,
        image: ByteArray
    ): List<ImageEditCommand.TextElement> {
        val userRequest = "Analyze visual hierarchy for copy: '$copyWrite' and provide JSON coordinates."
        val prompt = generatePromptUseCase.execute(
            GeneratePromptCommand(
                conversation = conversation,
                messages = emptyList(),
                userRequest = userRequest,
                promptType = PromptType.LAYOUT_ANALYSIS
            )
        )
        val json = aiClientPort.generateText(AiClientRequest(prompt.prompt, image)).message.extractTextContent()

        return try {
                objectMapper.readValue(
                    json,
                    object : TypeReference<List<ImageEditCommand.TextElement>>() {}
                ).also {
                    if (it.isEmpty()) {
                        throw CustomException(
                            ConversationApplicationExceptionCode.CONVERSATION_EMPTY_LAYOUT_ANALYSIS_RESPONSE,
                            "[Conversation] 레이아웃 분석 응답이 비어 있습니다."
                        )
                    }
                }
            } catch (ex: Exception) {
                throw CustomException(
                    ConversationApplicationExceptionCode.CONVERSATION_LAYOUT_ANALYSIS_FAILURE,
                    "[Conversation] 레이아웃 분석에 실패했습니다. 오류: ${ex.message}"
                )
            }
    }
}
