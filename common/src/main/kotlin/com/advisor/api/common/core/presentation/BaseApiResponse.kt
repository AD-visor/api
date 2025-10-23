package com.advisor.api.common.core.presentation

import org.springframework.http.HttpStatus

data class BaseApiResponse<T>(
    val success: Boolean,
    val message: String? = null,
    val data: T? = null,
    val httpStatus: HttpStatus,
    val customErrorCode: String? = null
)
