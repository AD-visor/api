package com.advisor.api.payment.domain.payment

import com.advisor.api.common.core.domain.AggregateRoot
import com.advisor.api.common.core.domain.vo.Money
import com.advisor.api.common.core.domain.vo.identifier.MemberId
import com.advisor.api.common.core.domain.vo.identifier.PaymentId
import com.advisor.api.common.core.domain.vo.identifier.PlanId
import com.advisor.api.common.exception.CustomException
import com.advisor.api.payment.domain.payment.vo.PaymentMethod
import com.advisor.api.payment.domain.payment.vo.PaymentStatus
import com.advisor.api.payment.domain.payment.vo.PgProvider
import java.time.Instant

class Payment private constructor(
    id: PaymentId,
    private val props: PaymentProps
): AggregateRoot<PaymentId>(id) {
    init { validate() }

    companion object {
        fun create(id: PaymentId, props: PaymentProps): Payment {
            return Payment(id, props)
        }

        fun of(id: PaymentId, props: PaymentProps): Payment {
            return Payment(id, props)
        }
    }

    private fun validate() {
        require(props.amount.value > 0) { CustomException(
            PaymentDomainExceptionCode.PAYMENT_AMOUNT_NON_POSITIVE,
            "[Payment] 결제 금액은 0보다 커야 합니다."
        ) }

        require((props.failureReason?.length ?: 0) <= 100) { CustomException(
            PaymentDomainExceptionCode.PAYMENT_FAILURE_REASON_LENGTH_EXCEEDED,
            "[Payment] 실패 사유는 최대 100자 입니다."
        ) }
    }

    val memberId: MemberId get() = props.memberId
    val planId: PlanId get() = props.planId
    val transactionId: String get() = props.transactionId
    val amount: Money get() = props.amount
    val currency: String get() = props.currency
    val pgProvider: PgProvider get() = props.pgProvider
    val status: PaymentStatus get() = props.status
    val paymentMethod: PaymentMethod get() = props.paymentMethod
    val failureReason: String? get() = props.failureReason
    val requestedAt: Instant get() = props.requestedAt
    val completedAt: Instant get() = props.completedAt
    val refundedAt: Instant? get() = props.refundedAt
}
