package com.advisor.api.payment.domain.payment

import com.advisor.api.common.core.domain.vo.identifier.PaymentId

interface PaymentStore {
    fun save(payment: Payment)
    fun loadById(id: PaymentId): Payment
}
