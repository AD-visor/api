package com.advisor.api.subscription.application.subscription.listener

import com.advisor.api.common.core.domain.vo.DomainEvent
import com.advisor.api.subscription.domain.subscription.event.SubscriptionRegisteredEvent
import com.advisor.api.subscription.domain.subscription.event.SubscriptionStatusUpdatedEvent
import com.advisor.api.subscription.port.inbound.subscription.RefreshSubscriptionViewUseCase
import com.advisor.api.subscription.port.inbound.subscription.listener.RefreshSubscriptionViewListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class RefreshSubscriptionViewListenerImpl(
    private val refreshSubscriptionViewUseCase: RefreshSubscriptionViewUseCase
): RefreshSubscriptionViewListener {
    @Async
    @TransactionalEventListener(
        classes = [
            SubscriptionRegisteredEvent::class,
            SubscriptionStatusUpdatedEvent::class,
        ],
        phase = TransactionPhase.AFTER_COMMIT
    )
    override fun handle(event: DomainEvent) {
        refreshSubscriptionViewUseCase.execute()
    }
}
