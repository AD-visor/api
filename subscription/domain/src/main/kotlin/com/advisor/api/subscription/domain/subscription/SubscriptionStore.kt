package com.advisor.api.subscription.domain.subscription

import com.advisor.api.common.core.domain.vo.identifier.MemberId
import com.advisor.api.common.core.domain.vo.identifier.SubscriptionId

interface SubscriptionStore {
    fun save(subscription: Subscription)
    fun loadById(id: SubscriptionId): Subscription
    fun loadByMemberId(memberId: MemberId): Subscription
    fun loadByIdAndMemberId(id: SubscriptionId, memberId: MemberId): Subscription
}
