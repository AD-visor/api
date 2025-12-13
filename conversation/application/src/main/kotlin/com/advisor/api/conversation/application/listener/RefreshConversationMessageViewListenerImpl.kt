package com.advisor.api.conversation.application.listener

import com.advisor.api.common.core.domain.vo.DomainEvent
import com.advisor.api.conversation.domain.conversation.event.*
import com.advisor.api.conversation.port.inbound.RefreshConversationMessageViewUseCase
import com.advisor.api.conversation.port.inbound.listener.RefreshConversationMessageViewListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class RefreshConversationMessageViewListenerImpl(
    private val refreshConversationMessageViewUseCase: RefreshConversationMessageViewUseCase
): RefreshConversationMessageViewListener {
    @Async
    @TransactionalEventListener(
        classes = [
            MemberMessageAddedEvent::class,
            AiResponseGeneratedEvent::class,
        ],
        phase = TransactionPhase.AFTER_COMMIT
    )
    override fun handle(event: DomainEvent) {
        refreshConversationMessageViewUseCase.execute()
    }
}
