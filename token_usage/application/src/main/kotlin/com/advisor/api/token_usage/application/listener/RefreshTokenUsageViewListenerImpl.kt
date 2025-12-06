package com.advisor.api.token_usage.application.listener

import com.advisor.api.common.core.domain.vo.DomainEvent
import com.advisor.api.token_usage.domain.event.TokenUsageRecordedEvent
import com.advisor.api.token_usage.port.inbound.RefreshTokenUsageViewUseCase
import com.advisor.api.token_usage.port.inbound.listener.RefreshTokenUsageViewListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class RefreshTokenUsageViewListenerImpl(
    private val refreshTokenUsageViewUseCase: RefreshTokenUsageViewUseCase
): RefreshTokenUsageViewListener {
    @Async
    @TransactionalEventListener(
        classes = [
            TokenUsageRecordedEvent::class,
        ],
        phase = TransactionPhase.AFTER_COMMIT
    )
    override fun handle(event: DomainEvent) {
        refreshTokenUsageViewUseCase.execute()
    }
}
