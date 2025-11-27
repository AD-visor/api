package com.advisor.api.payment.adapter.outbound.payment

import com.advisor.api.payment.domain.payment.PaymentView
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.annotation.Immutable
import java.time.Instant

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
    val amount: Float,

    @Column
    val currency: String,

    @Column
    val pgProvider: String,

    @Column
    val status: String,

    @Column
    val failureReason: String?,

    @Column
    val requestedAt: Instant,

    @Column
    val completedAt: Instant,

    @Column
    val refundedAt: Instant?
) {
    fun toModel(): PaymentView {
        return PaymentView(
            id = id,
            memberId = memberId,
            planId = planId,
            transactionId = transactionId,
            amount = amount,
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
