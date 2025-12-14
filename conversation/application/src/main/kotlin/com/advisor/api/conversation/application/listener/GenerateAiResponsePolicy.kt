package com.advisor.api.conversation.application.listener

import com.advisor.api.conversation.domain.conversation.event.MemberMessageAddedEvent
import com.advisor.api.conversation.port.inbound.GenerateAiResponseUseCase
import com.advisor.api.conversation.port.inbound.command.GenerateAiResponseCommand
import mu.KotlinLogging
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

private val logger = KotlinLogging.logger {}

@Component
class GenerateAiResponsePolicy(
    private val generateAiResponseUseCase: GenerateAiResponseUseCase
) {
    @Async
    @TransactionalEventListener(
        classes = [
            MemberMessageAddedEvent::class,
        ],
        phase = TransactionPhase.AFTER_COMMIT
    )
    fun handle(event: MemberMessageAddedEvent) {
        try {
            val command = GenerateAiResponseCommand(
                conversationId = event.conversationId,
                memberId = event.memberId,
                revisionOf = event.messageId
            )

            generateAiResponseUseCase.execute(command)
        } catch (e: Exception) {
            logger.error("AI 응답 생성 중 오류 발생: ${e.message}", e)
        }
    }
}
