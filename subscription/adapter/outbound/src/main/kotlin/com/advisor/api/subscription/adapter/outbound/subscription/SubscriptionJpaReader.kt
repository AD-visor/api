package com.advisor.api.subscription.adapter.outbound.subscription

import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface SubscriptionJpaReader: JpaRepository<SubscriptionViewEntity, Long> {
    fun findByMemberId(memberId: Long): Optional<SubscriptionViewEntity>
}
