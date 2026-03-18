package com.advisor.api.subscription.application.subscription

import com.advisor.api.subscription.port.inbound.subscription.GetActiveSubscriptionByMemberIdUseCase
import com.advisor.api.subscription.port.inbound.subscription.query.GetActiveSubscriptionByMemberIdQuery
import com.advisor.api.subscription.port.inbound.subscription.result.GetSubscriptionResult
import com.advisor.api.subscription.port.outbound.subscription.SubscriptionReader
import org.springframework.stereotype.Service

@Service
class GetActiveSubscriptionByMemberIdService(
    private val subscriptionReader: SubscriptionReader
): GetActiveSubscriptionByMemberIdUseCase {
    override fun execute(query: GetActiveSubscriptionByMemberIdQuery): GetSubscriptionResult {
        val subscription = subscriptionReader.findActiveByMemberId(query.memberId)

        return GetSubscriptionResult.fromModel(subscription)
    }
}
