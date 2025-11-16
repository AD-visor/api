package com.advisor.api.payment.infrastructure.payment

import com.advisor.api.common.exception.CustomExceptionCode
import com.advisor.api.common.exception.ErrorStatus

enum class PaymentInfraExceptionCode(
    override val status: ErrorStatus,
    override val code: String,
    override val message: String
): CustomExceptionCode {
    PAYMENT_NOT_FOUND(
        status = ErrorStatus.NOT_FOUND,
        code = "PAYMENT_NOT_FOUND",
        message = "[Payment] 해당 결제 정보를 찾을 수 없습니다."
    ),
}
