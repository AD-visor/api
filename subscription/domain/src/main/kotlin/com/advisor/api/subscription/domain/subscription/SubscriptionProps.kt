package com.advisor.api.subscription.domain.subscription

import com.advisor.api.common.core.domain.vo.identifier.MemberId
import com.advisor.api.common.core.domain.vo.identifier.PaymentId
import com.advisor.api.common.core.domain.vo.identifier.PlanId
import com.advisor.api.subscription.domain.subscription.vo.MonthlyUsage
import com.advisor.api.subscription.domain.subscription.vo.SubscriptionStatus
import java.time.Instant

data class SubscriptionProps(
    val subscriptionStatus: SubscriptionStatus,
    val monthlyUsage: MonthlyUsage,
    val planId: PlanId,
    val memberId: MemberId,
    val paymentId: PaymentId,
    val startedAt: Instant,
    val expiredAt: Instant,
    val isDeleted: Boolean,
    val deletedAt: Instant?
)
