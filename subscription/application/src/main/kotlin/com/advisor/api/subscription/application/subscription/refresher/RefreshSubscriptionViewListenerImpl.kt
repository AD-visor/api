package com.advisor.api.subscription.application.subscription.refresher

import com.advisor.api.common.core.domain.vo.DomainEvent
import com.advisor.api.subscription.domain.subscription.event.SubscriptionRegisteredEvent
import com.advisor.api.subscription.domain.subscription.event.SubscriptionStatusUpdatedEvent
import com.advisor.api.subscription.domain.subscription.event.TokensUsedEvent
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
internal class RefreshSubscriptionViewListenerImpl(
    private val refreshSubscriptionViewService: RefreshSubscriptionViewService
) {
    @Async
    @TransactionalEventListener(
        classes = [
            SubscriptionRegisteredEvent::class,
            SubscriptionStatusUpdatedEvent::class,
            TokensUsedEvent::class,
        ],
        phase = TransactionPhase.AFTER_COMMIT
    )
    fun handle(event: DomainEvent) {
        refreshSubscriptionViewService.execute()
    }
}