package com.advisor.api.conversation.application

import com.advisor.api.conversation.application.strategy.CopyWriteGenerationStrategy
import com.advisor.api.conversation.application.strategy.ImageGenerationStrategy
import com.advisor.api.conversation.application.strategy.LayoutAnalysisStrategy
import com.advisor.api.conversation.domain.conversation.Conversation
import com.advisor.api.conversation.domain.conversation.entity.ConversationMessageView
import com.advisor.api.media.port.outbound.command.ImageEditCommand
import org.springframework.stereotype.Component

@Component
class AiCreativeOrchestrator(
    private val copyWriteGenerationStrategy: CopyWriteGenerationStrategy,
    private val imageGenerationStrategy: ImageGenerationStrategy,
    private val layoutAnalysisStrategy: LayoutAnalysisStrategy
) {
    fun orchestrate(
        conversation: Conversation,
        messages: List<ConversationMessageView>,
        userRequest: String
    ): AiCreativeResult {

        // Step 1: 문구 생성
        val copy = copyWriteGenerationStrategy.generate(conversation, messages, userRequest)

        // Step 2: 배경 이미지 생성 (생성된 문구 활용)
        val imageResponse = imageGenerationStrategy.generate(conversation, messages, copy)

        // Step 3: 레이아웃 분석 (문구와 이미지 활용)
        val textElements = layoutAnalysisStrategy.analyze(conversation, copy, imageResponse.bytes)

        return AiCreativeResult(
            copyWrite = copy,
            imageBytes = imageResponse.bytes,
            textElements = textElements
        )
    }

    data class AiCreativeResult(
        val copyWrite: String,
        val imageBytes: ByteArray,
        val textElements: List<ImageEditCommand.TextElement>
    ) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as AiCreativeResult

            if (copyWrite != other.copyWrite) return false
            if (!imageBytes.contentEquals(other.imageBytes)) return false
            if (textElements != other.textElements) return false

            return true
        }

        override fun hashCode(): Int {
            var result = copyWrite.hashCode()
            result = 31 * result + imageBytes.contentHashCode()
            result = 31 * result + textElements.hashCode()
            return result
        }
    }
}
