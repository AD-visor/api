package com.advisor.api.payment.domain.payment

import com.advisor.api.common.core.domain.vo.Money
import com.advisor.api.common.core.domain.vo.identifier.MemberId
import com.advisor.api.common.core.domain.vo.identifier.PlanId
import com.advisor.api.payment.domain.payment.vo.PaymentMethod
import com.advisor.api.payment.domain.payment.vo.PaymentStatus
import com.advisor.api.payment.domain.payment.vo.PgProvider
import java.time.Instant

data class PaymentProps(
    val memberId: MemberId,
    val planId: PlanId,
    val transactionId: String,
    val amount: Money,
    val currency: String,
    val pgProvider: PgProvider,
    val status: PaymentStatus,
    val paymentMethod: PaymentMethod,
    val failureReason: String?,
    val requestedAt: Instant,
    val completedAt: Instant,
    val refundedAt: Instant?
)
