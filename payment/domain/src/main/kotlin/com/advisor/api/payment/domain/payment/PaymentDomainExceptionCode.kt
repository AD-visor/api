package com.advisor.api.payment.domain.payment

import com.advisor.api.common.exception.CustomExceptionCode
import com.advisor.api.common.exception.ErrorStatus

enum class PaymentDomainExceptionCode(
    override val status: ErrorStatus,
    override val code: String,
    override val message: String
): CustomExceptionCode {
    PAYMENT_INVALID_PG_PROVIDER(
        status = ErrorStatus.BAD_REQUEST,
        code = "PAYMENT_INVALID_PG_PROVIDER",
        message = "[Payment] 유효하지 않은 PG사 입니다."
    ),
    PAYMENT_INVALID_STATUS(
        status = ErrorStatus.BAD_REQUEST,
        code = "PAYMENT_INVALID_STATUS",
        message = "[Payment] 유효하지 않은 결제 상태입니다."
    ),
    PAYMENT_INVALID_METHOD(
        status = ErrorStatus.BAD_REQUEST,
        code = "PAYMENT_INVALID_METHOD",
        message = "[Payment] 유효하지 않은 결제 수단입니다."
    ),
    PAYMENT_AMOUNT_NON_POSITIVE(
        status = ErrorStatus.BAD_REQUEST,
        code = "PAYMENT_AMOUNT_NON_POSITIVE",
        message = "[Payment] 결제 금액은 양수여야 합니다."
    ),
    PAYMENT_FAILURE_REASON_LENGTH_EXCEEDED(
        status = ErrorStatus.BAD_REQUEST,
        code = "PAYMENT_FAILURE_REASON_LENGTH_EXCEEDED",
        message = "[Payment] 결제 실패 사유는 최대 100자 입니다."
    ),
}
