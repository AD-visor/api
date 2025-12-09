package com.advisor.api.conversation.application.listener

import com.advisor.api.common.core.domain.vo.DomainEvent
import com.advisor.api.conversation.domain.conversation.event.ConversationArchivedEvent
import com.advisor.api.conversation.domain.conversation.event.ConversationCreatedEvent
import com.advisor.api.conversation.domain.conversation.event.ConversationDeletedEvent
import com.advisor.api.conversation.domain.conversation.event.ConversationUpdatedEvent
import com.advisor.api.conversation.port.inbound.RefreshConversationViewUseCase
import com.advisor.api.conversation.port.inbound.listener.RefreshConversationViewListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class RefreshConversationViewListenerImpl(
    private val refreshConversationViewUseCase: RefreshConversationViewUseCase
): RefreshConversationViewListener {
    @Async
    @TransactionalEventListener(
        classes = [
            ConversationCreatedEvent::class,
            ConversationUpdatedEvent::class,
            ConversationDeletedEvent::class,
            ConversationArchivedEvent::class,

        ],
        phase = TransactionPhase.AFTER_COMMIT
    )
    override fun handle(event: DomainEvent) {
        refreshConversationViewUseCase.execute()
    }
}
