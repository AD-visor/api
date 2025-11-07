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

    @Column
    val subscriptionStatus: String,

    @Column
    val monthlyUsage: Long,

    @Column
    val memberId: Long,

    @Column
    val planId: Long,

    @Column
    val startAt: Instant,

    @Column
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
