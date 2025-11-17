package com.advisor.api.payment.domain.payment.vo

import com.advisor.api.common.exception.CustomException
import com.advisor.api.payment.domain.payment.PaymentDomainExceptionCode

class PaymentMethod private constructor(val value: String) {
    init { validate() }

    companion object {
        val CREDIT_CARD = PaymentMethod("CREDIT_CARD")
        val PAYPAL = PaymentMethod("PAYPAL")
        val BANK_TRANSFER = PaymentMethod("BANK_TRANSFER")

        fun create(value: String): PaymentMethod {
            return when (value) {
                "CREDIT_CARD" -> CREDIT_CARD
                "PAYPAL" -> PAYPAL
                "BANK_TRANSFER" -> BANK_TRANSFER
                else -> throw CustomException(
                    PaymentDomainExceptionCode.PAYMENT_INVALID_METHOD,
                    "[Payment] 유효하지 않은 결제 수단입니다."
                )
            }
        }
    }

    private fun validate() {}
}
