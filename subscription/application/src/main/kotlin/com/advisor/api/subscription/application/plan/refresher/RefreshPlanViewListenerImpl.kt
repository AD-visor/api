package com.advisor.api.subscription.application.plan.refresher

import com.advisor.api.common.core.domain.vo.DomainEvent
import com.advisor.api.subscription.domain.plan.event.PlanCreatedEvent
import com.advisor.api.subscription.domain.plan.event.PlanDeletedEvent
import com.advisor.api.subscription.domain.plan.event.PlanUndeletedEvent
import com.advisor.api.subscription.domain.plan.event.PlanUpdatedEvent
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
internal class RefreshPlanViewListenerImpl(
    private val refreshPlanViewService: RefreshPlanViewService
) {
    @Async
    @TransactionalEventListener(
        classes = [
            PlanCreatedEvent::class,
            PlanUpdatedEvent::class,
            PlanDeletedEvent::class,
            PlanUndeletedEvent::class
        ],
        phase = TransactionPhase.AFTER_COMMIT
    )
    fun handle(event: DomainEvent) {
        refreshPlanViewService.execute()
    }
}