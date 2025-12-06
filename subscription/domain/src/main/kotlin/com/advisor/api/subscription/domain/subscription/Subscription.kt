package com.advisor.api.subscription.domain.subscription

import com.advisor.api.common.core.domain.AggregateRoot
import com.advisor.api.common.core.domain.vo.identifier.MemberId
import com.advisor.api.common.core.domain.vo.identifier.PaymentId
import com.advisor.api.common.core.domain.vo.identifier.PlanId
import com.advisor.api.common.core.domain.vo.identifier.SubscriptionId
import com.advisor.api.subscription.domain.subscription.event.SubscriptionRegisteredEvent
import com.advisor.api.subscription.domain.subscription.event.SubscriptionStatusUpdatedEvent
import com.advisor.api.subscription.domain.subscription.event.SubscriptionUpdatedEvent
import com.advisor.api.subscription.domain.subscription.vo.MonthlyUsage
import com.advisor.api.subscription.domain.subscription.vo.SubscriptionStatus
import java.time.Instant

class Subscription(
    id: SubscriptionId,
    private val props: SubscriptionProps
) : AggregateRoot<SubscriptionId>(id) {
    init { validate() }

    companion object {
        fun create(id: SubscriptionId, props: SubscriptionProps): Subscription {
            val subscription = Subscription(id, props)
            subscription.addDomainEvent(SubscriptionRegisteredEvent())

            return subscription
        }

        fun of(id: SubscriptionId, props: SubscriptionProps): Subscription {
            return Subscription(id, props)
        }
    }

    fun update(
        newSubscriptionStatus: SubscriptionStatus,
        newMonthlyUsage: MonthlyUsage,
        newPlanId: PlanId,
        newMemberId: MemberId,
        newPaymentId: PaymentId,
        newStartedAt: Instant,
        newExpiredAt: Instant
    ): Subscription {
        val updatedSubscription = Subscription(id, props.copy(
            subscriptionStatus =  newSubscriptionStatus,
            monthlyUsage = newMonthlyUsage,
            planId = newPlanId,
            memberId = newMemberId,
            paymentId = newPaymentId,
            startedAt = newStartedAt,
            expiredAt = newExpiredAt
        ))

        updatedSubscription.addDomainEvent(SubscriptionUpdatedEvent())

        return updatedSubscription
    }

    fun cancel(): Subscription =
        updateStatus(SubscriptionStatus.create(SubscriptionStatus.CANCELED.value))

    fun expire(): Subscription =
        updateStatus(SubscriptionStatus.create(SubscriptionStatus.EXPIRED.value))

    fun suspend(): Subscription =
        updateStatus(SubscriptionStatus.create(SubscriptionStatus.SUSPENDED.value))

    fun resume(): Subscription =
        updateStatus(SubscriptionStatus.create(SubscriptionStatus.ACTIVE.value))


    private fun updateStatus(status: SubscriptionStatus): Subscription {
        val updatedSubscription = Subscription(
            id, props.copy(
                subscriptionStatus = status,
            )
        )

        updatedSubscription.addDomainEvent(SubscriptionStatusUpdatedEvent())

        return updatedSubscription
    }

    private fun validate() {}

    val subscriptionStatus: SubscriptionStatus get() = props.subscriptionStatus
    val monthlyUsage: MonthlyUsage get() = props.monthlyUsage
    val planId: PlanId get() = props.planId
    val memberId: MemberId get() = props.memberId
    val paymentId: PaymentId get() = props.paymentId
    val startedAt: Instant get() = props.startedAt
    val expiredAt: Instant get() = props.expiredAt
    val isDeleted: Boolean get() = props.isDeleted
    val deletedAt: Instant? get() = props.deletedAt
}
