package com.advisor.api.conversation.application

import com.advisor.api.conversation.application.strategy.IntegratedDesignStrategy
import com.advisor.api.conversation.domain.conversation.Conversation
import com.advisor.api.conversation.domain.conversation.entity.ConversationMessage
import com.advisor.api.conversation.domain.conversation.entity.ConversationMessageView
import com.advisor.api.conversation.domain.conversation.vo.MessageStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component
import mu.KotlinLogging

@Component
class AiCreativeOrchestrator(
    private val conversationMessageManager: ConversationMessageManager,
    private val integratedDesignStrategy: IntegratedDesignStrategy
) {
    private val logger = KotlinLogging.logger {}

    suspend fun orchestrate(
        conversation: Conversation,
        message: ConversationMessage,
        messages: List<ConversationMessageView>,
        userRequest: String
    ): AiCreativeResult {
        logger.info { "AI Creative Orchestration 시작" }

        var currentMessage = message

        try {
            // 단일 단계: 통합 디자인 생성
            currentMessage = withContext(Dispatchers.IO) {
                conversationMessageManager.updateMessageStatus(
                    conversation,
                    currentMessage,
                    MessageStatus.PROCESSING
                )
            }

            logger.info { "통합 디자인 생성 중..." }

            val completeDesign = integratedDesignStrategy.generate(
                conversation = conversation,
                messages = messages,
                userRequest = userRequest
            )

            logger.info { "통합 디자인 생성 완료: ${completeDesign.bytes.size} bytes" }

            return AiCreativeResult(
                imageBytes = completeDesign.bytes,
                conversationMessage = currentMessage
            )

        } catch (e: Exception) {
            logger.error(e) { "AI Creative Orchestration 실패" }
            throw e
        }
    }

    data class AiCreativeResult(
        val imageBytes: ByteArray,
        val conversationMessage: ConversationMessage
    ) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false
            other as AiCreativeResult
            if (!imageBytes.contentEquals(other.imageBytes)) return false
            return true
        }

        override fun hashCode(): Int {
            return imageBytes.contentHashCode()
        }
    }
}
