package com.advisor.api.subscription.domain.subscription.vo

import com.advisor.api.common.exception.CustomException
import com.advisor.api.subscription.domain.subscription.SubscriptionDomainExceptionCode

class SubscriptionStatus private constructor(val value: String) {
    init { validate() }

    companion object {
        val ACTIVE = SubscriptionStatus("ACTIVE")
        val SUSPENDED = SubscriptionStatus("SUSPENDED")
        val EXPIRED = SubscriptionStatus("EXPIRED")
        val CANCELED = SubscriptionStatus("CANCELED")

        val allowedStatus = setOf(ACTIVE, SUSPENDED, EXPIRED, CANCELED)

        fun create(value: String): SubscriptionStatus {
            return when (value) {
                "ACTIVE" -> ACTIVE
                "EXPIRED" -> EXPIRED
                "CANCELED" -> CANCELED
                else -> throw CustomException(
                    SubscriptionDomainExceptionCode.SUBSCRIPTION_INVALID_STATUS,
                    "[Subscription] 유효하지 않은 구독 상태입니다."
                )
            }
        }
    }

    private fun validate() {}

    override fun equals(other: Any?): Boolean = other is SubscriptionStatus && this.value == other.value
    override fun hashCode(): Int = value.hashCode()
}
