package com.advisor.api.payment.domain.payment

data class PaymentView(
    val id: Long,
    val memberId: Long,
    val planId: Long,
    val transactionId: String,
    val amount: Float,
    val currency: String,
    val pgProvider: String,
    val status: String,
    val failureReason: String?,
    val requestedAt: String,
    val completedAt: String,
    val refundedAt: String?
)
