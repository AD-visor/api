package com.advisor.api.common.exception

class CustomException (
    val code: CustomExceptionCode,
    val data: Any?
): RuntimeException()
