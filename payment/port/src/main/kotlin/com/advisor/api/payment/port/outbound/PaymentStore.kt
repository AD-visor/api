package com.advisor.api.payment.port.outbound

import com.advisor.api.common.core.domain.vo.identifier.PaymentId
import com.advisor.api.payment.domain.payment.Payment

interface PaymentStore {
    fun save(payment: Payment)
    fun loadById(id: PaymentId): Payment
}
