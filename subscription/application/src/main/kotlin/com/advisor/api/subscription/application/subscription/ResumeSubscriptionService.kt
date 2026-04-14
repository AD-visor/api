package com.advisor.api.subscription.application.subscription

import com.advisor.api.common.core.domain.vo.identifier.MemberId
import com.advisor.api.common.core.domain.vo.identifier.SubscriptionId
import com.advisor.api.common.core.infrastructure.DomainEventPublisher
import com.advisor.api.subscription.port.inbound.subscription.ResumeSubscriptionUseCase
import com.advisor.api.subscription.port.inbound.subscription.command.UpdateSubscriptionStatusCommand
import com.advisor.api.subscription.port.outbound.subscription.SubscriptionStore
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ResumeSubscriptionService(
    private val subscriptionStore: SubscriptionStore,
    private val domainEventPublisher: DomainEventPublisher
): ResumeSubscriptionUseCase {
    @Transactional
    override fun execute(command: UpdateSubscriptionStatusCommand) {
        val subscription = subscriptionStore.loadByIdAndMemberId(
            id = SubscriptionId(command.subscriptionId),
            memberId = MemberId(command.memberId),
        )
        val updatedSubscription = subscription.resume()

        subscriptionStore.save(updatedSubscription)

        domainEventPublisher.publishFrom(updatedSubscription)
    }
}
