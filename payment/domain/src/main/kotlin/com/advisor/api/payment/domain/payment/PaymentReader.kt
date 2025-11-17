package com.advisor.api.payment.domain.payment

import java.time.Instant

interface PaymentReader {
    fun findByAllByMemberId(
        memberId: Long,
        startAt: Instant?,
        endAt: Instant?,
    ): List<PaymentView>
}
