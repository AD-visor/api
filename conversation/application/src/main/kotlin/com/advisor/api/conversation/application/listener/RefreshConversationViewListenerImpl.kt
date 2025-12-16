package com.advisor.api.conversation.application.listener

import com.advisor.api.common.core.domain.vo.DomainEvent
import com.advisor.api.conversation.application.refresher.RefreshConversationViewService
import com.advisor.api.conversation.domain.conversation.event.*
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
internal class RefreshConversationViewListenerImpl(
    private val refreshConversationViewService: RefreshConversationViewService
) {
    @Async
    @TransactionalEventListener(
        classes = [
            ConversationCreatedEvent::class,
            ConversationUpdatedEvent::class,
            ConversationDeletedEvent::class,
            ConversationArchivedEvent::class,
            MemberMessageAddedEvent::class,
            AiResponseGeneratedEvent::class,
        ],
        phase = TransactionPhase.AFTER_COMMIT
    )
    fun handle(event: DomainEvent) {
        refreshConversationViewService.execute()
    }
}
