package com.advisor.api.subscription.application.subscription

import com.advisor.api.common.core.domain.vo.identifier.MemberId
import com.advisor.api.common.core.domain.vo.identifier.PaymentId
import com.advisor.api.common.core.domain.vo.identifier.PlanId
import com.advisor.api.common.core.domain.vo.identifier.SubscriptionId
import com.advisor.api.common.core.infrastructure.SnowFlakeIdUtil
import com.advisor.api.subscription.domain.subscription.Subscription
import com.advisor.api.subscription.domain.subscription.SubscriptionProps
import com.advisor.api.subscription.domain.subscription.SubscriptionReader
import com.advisor.api.subscription.domain.subscription.SubscriptionStore
import com.advisor.api.subscription.domain.subscription.vo.MonthlyUsage
import com.advisor.api.subscription.domain.subscription.vo.SubscriptionStatus
import com.advisor.api.subscription.port.inbound.subscription.RegisterSubscriptionUseCase
import com.advisor.api.subscription.port.inbound.subscription.command.RegisterSubscriptionCommand
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.ZoneId

@Service
class RegisterSubscriptionService(
    private val subscriptionStore: SubscriptionStore,
    private val subscriptionReader: SubscriptionReader,
    private val snowFlakeIdUtil: SnowFlakeIdUtil
): RegisterSubscriptionUseCase {
    override fun execute(command: RegisterSubscriptionCommand) {
        subscriptionReader.existsByPaymentId(command.paymentId)

        val startedAt = Instant.now()
        val expiredAt = calculateExpiredAt(startedAt, command.term)

        val subscriptionProps = SubscriptionProps(
            subscriptionStatus = SubscriptionStatus.create(SubscriptionStatus.ACTIVE.value),
            monthlyUsage = MonthlyUsage.create(0L),
            planId = PlanId(command.planId),
            memberId = MemberId(command.memberId),
            paymentId = PaymentId(command.paymentId),
            startedAt = startedAt,
            expiredAt = expiredAt,
            isDeleted = false,
            deletedAt = null
        )

        val subscription = Subscription.create(
            id = SubscriptionId(snowFlakeIdUtil.generateId()),
            props = subscriptionProps
        )

        subscriptionStore.save(subscription)
    }

    private fun calculateExpiredAt(startedAt: Instant, term: String): Instant {
        val zoneId = ZoneId.systemDefault()

        val expiredAt = startedAt
            .atZone(zoneId)

        when (term) {
            "MONTHLY" -> expiredAt.plusMonths(1)
            "YEARLY" -> expiredAt.plusYears(1)
        }

        return expiredAt.toInstant()
    }
}
