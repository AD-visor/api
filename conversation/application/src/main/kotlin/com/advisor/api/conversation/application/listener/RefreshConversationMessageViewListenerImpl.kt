package com.advisor.api.conversation.application.listener

import com.advisor.api.common.core.domain.event.AiResponseGeneratedEvent
import com.advisor.api.common.core.domain.vo.DomainEvent
import com.advisor.api.conversation.application.refresher.RefreshConversationMessageViewService
import com.advisor.api.conversation.domain.conversation.event.*
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
internal class RefreshConversationMessageViewListenerImpl(
    private val refreshConversationMessageViewService: RefreshConversationMessageViewService
) {
    @Async
    @TransactionalEventListener(
        classes = [
            MemberMessageAddedEvent::class,
            AiResponseGeneratedEvent::class,
            AiMessageCreatedEvent::class,
            MessageStatusUpdatedEvent::class
        ],
        phase = TransactionPhase.AFTER_COMMIT
    )
    fun handle(event: DomainEvent) {
        refreshConversationMessageViewService.execute()
    }
}
