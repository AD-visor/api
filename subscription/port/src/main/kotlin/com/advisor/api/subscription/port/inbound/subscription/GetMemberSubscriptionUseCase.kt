package com.advisor.api.subscription.port.inbound.subscription

import com.advisor.api.subscription.port.inbound.subscription.query.GetMemberSubscriptionQuery
import com.advisor.api.subscription.port.inbound.subscription.view.SubscriptionView

interface GetMemberSubscriptionUseCase {
    fun execute(query: GetMemberSubscriptionQuery): SubscriptionView
}
