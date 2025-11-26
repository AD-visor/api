package com.advisor.api.subscription.adapter.outbound.subscription

import com.advisor.api.common.core.domain.vo.identifier.MemberId
import com.advisor.api.common.core.domain.vo.identifier.PlanId
import com.advisor.api.common.core.domain.vo.identifier.SubscriptionId
import com.advisor.api.subscription.domain.subscription.Subscription
import com.advisor.api.subscription.domain.subscription.SubscriptionProps
import com.advisor.api.subscription.domain.subscription.vo.MonthlyUsage
import com.advisor.api.subscription.domain.subscription.vo.SubscriptionStatus
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.Instant

@Entity
@Table(name = "subscription")
class SubscriptionEntity(
    @Id
    val id: Long,

    @Column(nullable = false)
    val subscriptionStatus: String,

    @Column(nullable = false)
    val monthlyUsage: Long,

    @Column(nullable = false)
    val planId: Long,

    @Column(nullable = false)
    val memberId: Long,

    @Column(nullable = false)
    val startedAt: Instant,

    @Column(nullable = false)
    val expiredAt: Instant,

    @Column(nullable = false)
    val isDeleted: Boolean,

    @Column
    val deletedAt: Instant?,
) {
    companion object {
        fun fromDomain(domain: Subscription): SubscriptionEntity {
            return SubscriptionEntity(
                id = domain.id.value,
                subscriptionStatus = domain.subscriptionStatus.value,
                monthlyUsage = domain.monthlyUsage.value,
                planId = domain.planId.value,
                memberId = domain.memberId.value,
                startedAt = domain.startedAt,
                expiredAt = domain.expiredAt,
                isDeleted = domain.isDeleted,
                deletedAt = domain.deletedAt
            )
        }
    }

    fun toDomain(): Subscription {
        val props = SubscriptionProps(
            subscriptionStatus = SubscriptionStatus.create(subscriptionStatus),
            monthlyUsage = MonthlyUsage.create(monthlyUsage),
            planId = PlanId(planId),
            memberId = MemberId(memberId),
            startedAt = startedAt,
            expiredAt = expiredAt,
            isDeleted = isDeleted,
            deletedAt = deletedAt
        )

        return Subscription.of(SubscriptionId(id), props)
    }
}
