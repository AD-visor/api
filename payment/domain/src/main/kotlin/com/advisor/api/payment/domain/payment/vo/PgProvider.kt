package com.advisor.api.payment.domain.payment.vo

import com.advisor.api.common.exception.CustomException
import com.advisor.api.payment.domain.payment.PaymentDomainExceptionCode

class PgProvider private constructor(val value: String) {
    init { validate() }

    companion object {
        val KAKAO_PAY = PgProvider("KAKAO_PAY")
        val TOSS_PAY = PgProvider("TOSS_PAY")

        fun create(value: String): PgProvider {
            return when (value) {
                "KAKAO_PAY" -> KAKAO_PAY
                "TOSS_PAY" -> TOSS_PAY
                else -> throw CustomException(
                    PaymentDomainExceptionCode.PAYMENT_INVALID_PG_PROVIDER,
                    "[PgProvider] 유효하지 않은 PG사 입니다."
                )
            }
        }
    }

    private fun validate() {}
}