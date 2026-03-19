package com.advisor.api.subscription.port.outbound.subscription

import com.advisor.api.subscription.port.inbound.subscription.view.SubscriptionView

interface SubscriptionReader {
    fun findById(id: Long): SubscriptionView
    fun findByMemberId(memberId: Long): SubscriptionView
    fun findActiveByMemberId(memberId: Long): SubscriptionView
    fun existsByPaymentId(paymentId: Long)
    fun refreshView()
}
