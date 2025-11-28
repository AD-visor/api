package com.advisor.api.subscription.domain.subscription

interface SubscriptionReader {
    fun findById(id: Long): SubscriptionView
    fun findByMemberId(memberId: Long): SubscriptionView
    fun findActiveByMemberId(memberId: Long): SubscriptionView
    fun existsByPaymentId(paymentId: Long)
}
