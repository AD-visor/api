package com.advisor.api.subscription.application.subscription

import com.advisor.api.subscription.port.inbound.subscription.GetMemberSubscriptionUseCase
import com.advisor.api.subscription.port.inbound.subscription.query.GetMemberSubscriptionQuery
import com.advisor.api.subscription.port.inbound.subscription.view.SubscriptionView
import com.advisor.api.subscription.port.outbound.subscription.SubscriptionReader
import org.springframework.stereotype.Service

@Service
class GetMemberSubscriptionService(
    private val subscriptionReader: SubscriptionReader
): GetMemberSubscriptionUseCase {
    override fun execute(query: GetMemberSubscriptionQuery): SubscriptionView {
        return subscriptionReader.findByMemberId(query.memberId)
    }
}
