package com.advisor.api.iam.application.member.listener

import com.advisor.api.common.core.domain.vo.DomainEvent
import com.advisor.api.iam.domain.member.event.MemberCreatedEvent
import com.advisor.api.iam.domain.member.event.MemberDeletedEvent
import com.advisor.api.iam.port.inbound.member.listener.RefreshMemberViewListener
import com.advisor.api.iam.port.inbound.member.usecase.RefreshMemberViewUseCase
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class RefreshMemberViewListenerImpl(
    private val refreshMemberViewUseCase: RefreshMemberViewUseCase
): RefreshMemberViewListener {
    @Async
    @TransactionalEventListener(
        classes = [
            MemberCreatedEvent::class,
            MemberDeletedEvent::class
        ],
        phase = TransactionPhase.AFTER_COMMIT
    )
    override fun handle(event: DomainEvent) {
        refreshMemberViewUseCase.execute()
    }
}
