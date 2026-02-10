package com.advisor.api.conversation.application

import com.advisor.api.conversation.application.strategy.CopyWriteGenerationStrategy
import com.advisor.api.conversation.application.strategy.ImageGenerationStrategy
import com.advisor.api.conversation.application.strategy.LayoutAnalysisStrategy
import com.advisor.api.conversation.domain.conversation.Conversation
import com.advisor.api.conversation.domain.conversation.entity.ConversationMessage
import com.advisor.api.conversation.domain.conversation.entity.ConversationMessageView
import com.advisor.api.conversation.domain.conversation.vo.MessageStatus.Companion.ANALYSING_IMAGE
import com.advisor.api.conversation.domain.conversation.vo.MessageStatus.Companion.COPY_WRITING
import com.advisor.api.conversation.domain.conversation.vo.MessageStatus.Companion.GENERATING_IMAGE
import com.advisor.api.media.port.outbound.command.ImageEditCommand
import org.springframework.stereotype.Component

@Component
class AiCreativeOrchestrator(
    private val conversationMessageManager: ConversationMessageManager,
    private val copyWriteGenerationStrategy: CopyWriteGenerationStrategy,
    private val imageGenerationStrategy: ImageGenerationStrategy,
    private val layoutAnalysisStrategy: LayoutAnalysisStrategy
) {
    suspend fun orchestrate(
        conversation: Conversation,
        message: ConversationMessage,
        messages: List<ConversationMessageView>,
        userRequest: String
    ): AiCreativeResult {
        var currentMessage = message

        // Step 1: COPY WRITING
        currentMessage = conversationMessageManager.updateMessageStatus(conversation, currentMessage, COPY_WRITING)
        val copy = copyWriteGenerationStrategy.generate(conversation, messages, userRequest)

        // Step 2: IMAGE GENERATION
        currentMessage = conversationMessageManager.updateMessageStatus(conversation, currentMessage, GENERATING_IMAGE)
        val imageResponse = imageGenerationStrategy.generate(conversation, messages, copy)

        // Step 3: LAYOUT ANALYSIS
        currentMessage = conversationMessageManager.updateMessageStatus(conversation, currentMessage, ANALYSING_IMAGE)
        val textElements = layoutAnalysisStrategy.analyze(conversation, copy, imageResponse.bytes)

        return AiCreativeResult(
            copyWrite = copy,
            imageBytes = imageResponse.bytes,
            textElements = textElements,
            conversationMessage = currentMessage
        )
    }

    data class AiCreativeResult(
        val copyWrite: String,
        val imageBytes: ByteArray,
        val textElements: List<ImageEditCommand.TextElement>,
        val conversationMessage: ConversationMessage
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
