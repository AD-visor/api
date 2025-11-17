package com.advisor.api.payment.infrastructure.payment

import com.advisor.api.payment.domain.payment.PaymentReader
import com.advisor.api.payment.domain.payment.PaymentView
import org.springframework.stereotype.Repository
import java.time.Instant

@Repository
class PaymentReaderImpl(
    private val jpaReader: PaymentJpaReader
): PaymentReader {
    override fun findByAllByMemberId(memberId: Long, startAt: Instant?, endAt: Instant?): List<PaymentView> {
        val entities = jpaReader.findAllByMemberId(memberId, startAt, endAt)

        return entities.map { it.toModel() }
    }
}
