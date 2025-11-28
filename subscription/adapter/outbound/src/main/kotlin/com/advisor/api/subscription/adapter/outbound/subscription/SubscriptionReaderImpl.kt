package com.advisor.api.subscription.adapter.outbound.subscription

import com.advisor.api.common.exception.CustomException
import com.advisor.api.subscription.domain.subscription.SubscriptionReader
import com.advisor.api.subscription.domain.subscription.SubscriptionView
import com.advisor.api.subscription.domain.subscription.vo.SubscriptionStatus
import org.springframework.stereotype.Repository

@Repository
class SubscriptionReaderImpl(
    private val jpaReader: SubscriptionJpaReader
): SubscriptionReader {
    override fun findById(id: Long): SubscriptionView {
        val entity = jpaReader.findById(id).orElseThrow { CustomException(
            SubscriptionInfraExceptionCode.SUBSCRIPTION_NOT_FOUND,
            "[Subscription] ${id}에 해당하는 Subscription를 찾을 수 없습니다."
        ) }

        return entity.toModel()
    }

    override fun findByMemberId(memberId: Long): SubscriptionView {
        val entity = jpaReader.findByMemberId(memberId).orElseThrow { CustomException(
            SubscriptionInfraExceptionCode.SUBSCRIPTION_NOT_FOUND,
            "[Subscription] memberId: ${memberId}에 해당하는 Subscription를 찾을 수 없습니다."
        ) }

        return entity.toModel()
    }

    override fun findActiveByMemberId(memberId: Long): SubscriptionView {
        val entity = jpaReader.findByMemberIdAndSubscriptionStatus(
            memberId,
            SubscriptionStatus.ACTIVE.value
        ).orElseThrow { CustomException(
            SubscriptionInfraExceptionCode.SUBSCRIPTION_NOT_FOUND,
            "[Subscription] memberId: ${memberId}에 해당하는 활성화된 Subscription를 찾을 수 없습니다."
        ) }

        return entity.toModel()
    }

    override fun existsByPaymentId(paymentId: Long) {
        val result = jpaReader.existsByPaymentId(paymentId)
        if (result) { throw CustomException(
            SubscriptionInfraExceptionCode.SUBSCRIPTION_ALREADY_EXISTS,
            "[Subscription] paymentId: ${paymentId}에 해당하는 Subscription가 이미 존재합니다."
        ) }
    }
}
