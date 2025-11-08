package com.advisor.api.subscription.infrastructure.subscription

import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface SubscriptionJpaStore: JpaRepository<SubscriptionEntity, Long> {
    fun findByMemberId(memberId: Long): Optional<SubscriptionEntity>
}
