package com.advisor.api.subscription.application.plan.listener

import com.advisor.api.common.core.domain.vo.DomainEvent
import com.advisor.api.subscription.domain.plan.event.PlanCreatedEvent
import com.advisor.api.subscription.domain.plan.event.PlanDeletedEvent
import com.advisor.api.subscription.domain.plan.event.PlanUndeletedEvent
import com.advisor.api.subscription.domain.plan.event.PlanUpdatedEvent
import com.advisor.api.subscription.port.inbound.plan.RefreshPlanViewUseCase
import com.advisor.api.subscription.port.inbound.plan.listener.RefreshPlanViewListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class RefreshPlanViewListenerImpl(
    private val refreshPlanViewUseCase: RefreshPlanViewUseCase
): RefreshPlanViewListener {
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
    override fun handle(event: DomainEvent) {
        refreshPlanViewUseCase.execute()
    }
}
