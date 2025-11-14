package com.advisor.api.payment.domain.payment.vo

import com.advisor.api.common.exception.CustomException
import com.advisor.api.payment.domain.payment.PaymentDomainExceptionCode

class PaymentStatus private constructor(val value: String) {
    init { validate() }

    companion object {
        val PENDING = PaymentStatus("PENDING")
        val COMPLETED = PaymentStatus("COMPLETED")
        val FAILED = PaymentStatus("FAILED")

        fun create(value: String): PaymentStatus {
            return when (value) {
                "PENDING" -> PENDING
                "COMPLETED" -> COMPLETED
                "FAILED" -> FAILED
                else -> throw CustomException(
                    PaymentDomainExceptionCode.PAYMENT_INVALID_STATUS,
                    "[Payment] 유효하지 않은 결제 상태입니다."
                )
            }
        }
    }

    private fun validate() {}
}
