package com.advisor.api.token_usage.application

import com.advisor.api.common.core.domain.vo.identifier.*
import com.advisor.api.common.core.infrastructure.DomainEventPublisher
import com.advisor.api.common.core.infrastructure.SnowFlakeIdUtil
import com.advisor.api.token_usage.domain.TokenUsage
import com.advisor.api.token_usage.domain.TokenUsageProps
import com.advisor.api.token_usage.port.outbound.TokenUsageStore
import com.advisor.api.token_usage.port.inbound.RecordTokenUsageUseCase
import com.advisor.api.token_usage.port.inbound.command.RecordTokenUsageCommand
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RecordTokenUsageService(
    private val tokenUsageStore: TokenUsageStore,
    private val snowFlakeIdUtil: SnowFlakeIdUtil,
    private val domainEventPublisher: DomainEventPublisher
) : RecordTokenUsageUseCase {
    @Transactional
    override fun execute(command: RecordTokenUsageCommand) {
        createTokenUsage(
            memberId = MemberId(command.memberId),
            subscriptionId = SubscriptionId(command.subscriptionId),
            planId = PlanId(command.planId),
            usedTokens = command.usedTokens,
            conversationMessageId = ConversationMessageId(command.conversationMessageId)
        )
    }

    private fun createTokenUsage(
        memberId: MemberId,
        subscriptionId: SubscriptionId,
        planId: PlanId,
        usedTokens: Long,
        conversationMessageId: ConversationMessageId
    ) {
        val tokenUsageProps = TokenUsageProps(
            memberId = memberId,
            planId = planId,
            subscriptionId = subscriptionId,
            usedTokens = usedTokens,
            usedAt = java.time.Instant.now(),
            conversationMessageId = conversationMessageId
        )

        val tokenUsage = TokenUsage.create(
            TokenUsageId(snowFlakeIdUtil.generateId()),
            tokenUsageProps
        )

        tokenUsageStore.save(tokenUsage)

        domainEventPublisher.publishFrom(tokenUsage)
    }
}
