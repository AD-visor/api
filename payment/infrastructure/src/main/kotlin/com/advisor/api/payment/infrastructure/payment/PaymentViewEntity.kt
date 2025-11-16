package com.advisor.api.payment.infrastructure.payment

import com.advisor.api.payment.domain.payment.PaymentView
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.annotation.Immutable

@Entity
@Immutable
@Table(name = "vw_payment")
class PaymentViewEntity(
    @Id
    val id: Long,

    @Column
    val memberId: Long,

    @Column
    val planId: Long,

    @Column
    val transactionId: String,

    @Column
    val amount: Long,

    @Column
    val currency: String,

    @Column
    val pgProvider: String,

    @Column
    val status: String,

    @Column
    val failureReason: String?,

    @Column
    val requestedAt: String,

    @Column
    val completedAt: String,

    @Column
    val refundedAt: String?
) {
    fun toModel(): PaymentView {
        return PaymentView(
            id = id,
            memberId = memberId,
            planId = planId,
            transactionId = transactionId,
            amount = amount.toFloat(),
            currency = currency,
            pgProvider = pgProvider,
            status = status,
            failureReason = failureReason,
            requestedAt = requestedAt,
            completedAt = completedAt,
            refundedAt = refundedAt
        )
    }
}
