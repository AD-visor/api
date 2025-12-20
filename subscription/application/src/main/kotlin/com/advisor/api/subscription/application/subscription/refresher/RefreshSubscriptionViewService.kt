package com.advisor.api.subscription.application.subscription.refresher

import com.advisor.api.subscription.domain.subscription.SubscriptionReader
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
internal class RefreshSubscriptionViewService(
    private val subscriptionReader: SubscriptionReader
) {
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun execute() {
        subscriptionReader.refreshView()
    }
}