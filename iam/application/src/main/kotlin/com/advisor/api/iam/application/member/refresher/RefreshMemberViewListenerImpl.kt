package com.advisor.api.iam.application.member.refresher

import com.advisor.api.common.core.domain.vo.DomainEvent
import com.advisor.api.iam.domain.member.event.MemberCreatedEvent
import com.advisor.api.iam.domain.member.event.MemberDeletedEvent
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
internal class RefreshMemberViewListenerImpl(
    private val refreshMemberViewService: RefreshMemberViewService
) {
    @Async
    @TransactionalEventListener(
        classes = [
            MemberCreatedEvent::class,
            MemberDeletedEvent::class
        ],
        phase = TransactionPhase.AFTER_COMMIT
    )
    fun handle(event: DomainEvent) {
        refreshMemberViewService.execute()
    }
}
