package com.advisor.api.subscription.infrastructure.subscription

import com.advisor.api.subscription.domain.subscription.SubscriptionView
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.annotation.Immutable
import java.time.Instant

@Entity
@Immutable
@Table(name = "vw_subscription")
class SubscriptionViewEntity(
    @Id
    val id: Long,

    @Column(nullable = false)
    val subscriptionStatus: String,

    @Column(nullable = false)
    val monthlyUsage: Long,

    @Column(nullable = false)
    val memberId: Long,

    @Column(nullable = false)
    val planId: Long,

    @Column(nullable = false)
    val startAt: Instant,

    @Column(nullable = false)
    val expiredAt: Instant,
) {
    fun toModel(): SubscriptionView {
        return SubscriptionView(
            id = id,
            subscriptionStatus = subscriptionStatus,
            monthlyUsage = monthlyUsage,
            memberId = memberId,
            planId = planId,
            startAt = startAt,
            expiredAt = expiredAt
        )
    }
}
