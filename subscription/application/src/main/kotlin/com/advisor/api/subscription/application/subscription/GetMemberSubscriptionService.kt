package com.advisor.api.subscription.application.subscription

import com.advisor.api.subscription.domain.subscription.SubscriptionReader
import com.advisor.api.subscription.port.inbound.subscription.GetMemberSubscriptionUseCase
import com.advisor.api.subscription.port.inbound.subscription.query.GetMemberSubscriptionQuery
import com.advisor.api.subscription.port.inbound.subscription.result.GetSubscriptionResult
import org.springframework.stereotype.Service

@Service
class GetMemberSubscriptionService(
    private val subscriptionReader: SubscriptionReader
): GetMemberSubscriptionUseCase {
    override fun execute(query: GetMemberSubscriptionQuery): GetSubscriptionResult {
        val subscriptionView = subscriptionReader.findByMemberId(query.memberId)

        return GetSubscriptionResult.fromModel(subscriptionView)
    }
}
