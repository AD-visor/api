package com.advisor.api.subscription.port.inbound.subscription

import com.advisor.api.subscription.port.inbound.subscription.query.GetActiveSubscriptionByMemberIdQuery
import com.advisor.api.subscription.port.inbound.subscription.result.GetSubscriptionResult

interface GetActiveSubscriptionByMemberIdUseCase {
    fun execute(query: GetActiveSubscriptionByMemberIdQuery): GetSubscriptionResult
}
