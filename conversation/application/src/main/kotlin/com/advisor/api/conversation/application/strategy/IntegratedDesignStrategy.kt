package com.advisor.api.conversation.application.strategy

import com.advisor.api.ai_prompt_core.model.MultimodalContent
import com.advisor.api.ai_prompt_core.model.PromptType
import com.advisor.api.conversation.domain.conversation.Conversation
import com.advisor.api.conversation.domain.conversation.entity.ConversationMessageView
import com.advisor.api.conversation.port.inbound.command.GeneratePromptCommand
import com.advisor.api.conversation.port.inbound.prompt.GeneratePromptUseCase
import com.advisor.api.conversation.port.outbound.AiClientPort
import com.advisor.api.conversation.port.outbound.request.AiClientRequest
import com.advisor.api.conversation.port.outbound.response.AiImageResponse
import mu.KotlinLogging
import org.springframework.stereotype.Component

@Component
class IntegratedDesignStrategy(
    private val aiClientPort: AiClientPort,
    private val generatePromptUseCase: GeneratePromptUseCase
) {
    private val logger = KotlinLogging.logger {}

    suspend fun generate(
        conversation: Conversation,
        messages: List<ConversationMessageView>,
        userRequest: String
    ): AiImageResponse {
        logger.info { "통합 디자인 생성 요청: $userRequest" }

        // 1. 간단한 카피 추출 (userRequest에서)
        val copyWrite = generateMarketingCopy(
            conversation,
            messages,
            userRequest
        )
        logger.info { "생성된 카피: $copyWrite" }

        // 2. 메타데이터 추출
        val targetAudience = extractTargetAudience(conversation, userRequest)
        val brandStyle = extractBrandStyle(conversation, userRequest)

        logger.info { "타겟: $targetAudience, 스타일: $brandStyle" }

        // 3. 통합 디자인 프롬프트 생성
        val prompt = generatePromptUseCase.execute(
            GeneratePromptCommand(
                conversation = conversation,
                messages = messages,
                userRequest = userRequest,
                promptType = PromptType.INTEGRATED_DESIGN,
                additionalData = mapOf(
                    "copyWrite" to copyWrite,
                    "targetAudience" to targetAudience,
                    "brandStyle" to brandStyle
                )
            )
        )

        // 4. 이미지 생성
        logger.info { "AI 이미지 생성 시작" }
        val response = aiClientPort.generateImage(AiClientRequest(prompt.prompt))

        logger.info { "통합 디자인 생성 완료: ${response.bytes.size} bytes" }

        return response
    }

    private fun extractCopyWrite(userRequest: String): String {
        return when {
            userRequest.contains("\"") -> {
                // "텍스트" 형태에서 추출
                val start = userRequest.indexOf("\"")
                val end = userRequest.indexOf("\"", start + 1)
                if (end > start) {
                    userRequest.substring(start + 1, end)
                } else {
                    userRequest.take(15)
                }
            }
            userRequest.length <= 20 -> userRequest
            else -> userRequest.take(15) + "..."
        }
    }

    private suspend fun generateMarketingCopy(
        conversation: Conversation,
        messages: List<ConversationMessageView>,
        userRequest: String
    ): String {
        logger.info { "광고 카피 생성 중..." }

        val prompt = generatePromptUseCase.execute(
            GeneratePromptCommand(
                conversation = conversation,
                messages = messages,
                userRequest = userRequest,
                promptType = PromptType.COPY_WRITING
            )
        )

        val response = aiClientPort.generateText(AiClientRequest(prompt.prompt))

        val copyWrite = response.message.content
            .filterIsInstance<MultimodalContent.Text>()
            .joinToString(" ") { it.value }
            .trim()

        return copyWrite
    }

    private fun extractTargetAudience(
        conversation: Conversation,
        userRequest: String
    ): String {
        return when {
            userRequest.contains("20대") || userRequest.contains("젊은") -> "20대 젊은층"
            userRequest.contains("30대") || userRequest.contains("직장인") -> "30대 직장인"
            userRequest.contains("40대") || userRequest.contains("중년") -> "40-50대 중년층"
            else -> "20-30대 일반 소비자"
        }
    }

    private fun extractBrandStyle(
        conversation: Conversation,
        userRequest: String
    ): String {
        return when {
            userRequest.contains("미니멀") || userRequest.contains("심플") -> "Minimalist"
            userRequest.contains("강렬") || userRequest.contains("임팩트") -> "Bold"
            userRequest.contains("따뜻") || userRequest.contains("포근") -> "Warm"
            userRequest.contains("프리미엄") || userRequest.contains("고급") -> "Premium"
            userRequest.contains("활기") || userRequest.contains("재미") -> "Energetic"
            else -> "Minimalist"
        }
    }
}
