package com.advisor.api.subscription.application.subscription

import com.advisor.api.subscription.domain.subscription.SubscriptionReader
import com.advisor.api.subscription.port.inbound.subscription.RefreshSubscriptionViewUseCase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
class RefreshSubscriptionViewService(
    private val subscriptionReader: SubscriptionReader
): RefreshSubscriptionViewUseCase {
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    override fun execute() {
        subscriptionReader.refreshView()
    }
}
