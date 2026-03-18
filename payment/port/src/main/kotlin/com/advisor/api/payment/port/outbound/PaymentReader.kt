package com.advisor.api.payment.port.outbound

import com.advisor.api.payment.port.inbound.PaymentView
import java.time.Instant

interface PaymentReader {
    fun findByAllByMemberId(
        memberId: Long,
        startAt: Instant?,
        endAt: Instant?,
    ): List<PaymentView>
}
