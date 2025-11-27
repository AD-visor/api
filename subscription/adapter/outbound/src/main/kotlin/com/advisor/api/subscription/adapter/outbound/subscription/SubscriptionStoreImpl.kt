package com.advisor.api.subscription.adapter.outbound.subscription

import com.advisor.api.common.core.domain.vo.identifier.MemberId
import com.advisor.api.common.core.domain.vo.identifier.SubscriptionId
import com.advisor.api.common.exception.CustomException
import com.advisor.api.subscription.domain.subscription.Subscription
import com.advisor.api.subscription.domain.subscription.SubscriptionStore
import org.springframework.stereotype.Repository

@Repository
class SubscriptionStoreImpl(
    private val jpaStore: SubscriptionJpaStore,
): SubscriptionStore {
    override fun save(subscription: Subscription) {
        val jpaEntity = SubscriptionEntity.fromDomain(subscription)
        jpaStore.save(jpaEntity)
    }

    override fun loadById(id: SubscriptionId): Subscription {
        val jpaEntity = jpaStore.findById(id.value).orElseThrow { CustomException(
            SubscriptionInfraExceptionCode.SUBSCRIPTION_NOT_FOUND,
            "[Subscription] ${id}에 해당하는 Subscription를 찾을 수 없습니다."
        ) }

        return jpaEntity.toDomain()
    }

    override fun loadByMemberId(memberId: MemberId): Subscription {
        val jpaEntity = jpaStore.findByMemberId(memberId.value).orElseThrow { CustomException(
            SubscriptionInfraExceptionCode.SUBSCRIPTION_NOT_FOUND,
            "[Subscription] memberId: ${memberId}에 해당하는 Subscription를 찾을 수 없습니다."
        ) }

        return jpaEntity.toDomain()
    }
}
