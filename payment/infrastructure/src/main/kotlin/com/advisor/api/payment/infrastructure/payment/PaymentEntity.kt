package com.advisor.api.payment.infrastructure.payment

import com.advisor.api.common.core.domain.vo.Money
import com.advisor.api.common.core.domain.vo.identifier.MemberId
import com.advisor.api.common.core.domain.vo.identifier.PaymentId
import com.advisor.api.common.core.domain.vo.identifier.PlanId
import com.advisor.api.payment.domain.payment.Payment
import com.advisor.api.payment.domain.payment.PaymentProps
import com.advisor.api.payment.domain.payment.vo.PaymentStatus
import com.advisor.api.payment.domain.payment.vo.PgProvider
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.Instant

@Entity
@Table(name = "payment")
class PaymentEntity(
    @Id
    val id: Long,

    @Column
    val memberId: Long,

    @Column
    val planId: Long,

    @Column
    val amount: Float,

    @Column
    val currency: String,

    @Column
    val pgProvider: String,

    @Column
    val status: String,

    @Column
    val transactionId: String,

    @Column
    val failureReason: String?,

    @Column
    val requestedAt: Instant,

    @Column
    val completedAt: Instant,

    @Column
    val refundedAt: Instant?
) {
    companion object {
        fun fromDomain(domain: Payment): PaymentEntity {
            return PaymentEntity(
                id = domain.id.value,
                memberId = domain.memberId.value,
                planId = domain.planId.value,
                amount = domain.amount.value,
                currency = domain.currency,
                pgProvider = domain.pgProvider.value,
                status = domain.status.value,
                transactionId = domain.transactionId,
                failureReason = domain.failureReason,
                requestedAt = domain.requestedAt,
                completedAt = domain.completedAt,
                refundedAt = domain.refundedAt
            )
        }
    }

    fun toDomain(): Payment {
        val props = PaymentProps(
            memberId = MemberId(memberId),
            planId = PlanId(planId),
            transactionId = transactionId,
            amount = Money.create(amount),
            currency = currency,
            pgProvider = PgProvider.create(pgProvider),
            status = PaymentStatus.create(status),
            failureReason = failureReason,
            requestedAt = requestedAt,
            completedAt = completedAt,
            refundedAt = refundedAt
        )

        return Payment.of(PaymentId(id), props)
    }
}
