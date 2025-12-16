package com.advisor.api.token_usage.application.refresher

import com.advisor.api.common.core.domain.vo.DomainEvent
import com.advisor.api.token_usage.domain.event.TokenUsageRecordedEvent
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
internal class RefreshTokenUsageViewListenerImpl(
    private val refreshTokenUsageViewService: RefreshTokenUsageViewService
) {
    @Async
    @TransactionalEventListener(
        classes = [
            TokenUsageRecordedEvent::class,
        ],
        phase = TransactionPhase.AFTER_COMMIT
    )
    fun handle(event: DomainEvent) {
        refreshTokenUsageViewService.execute()
    }
}
