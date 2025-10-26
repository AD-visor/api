package com.advisor.api.common.exception

import org.springframework.http.HttpStatus

object ErrorStatusMapper {
    fun toHttpStatus(status: ErrorStatus): HttpStatus
    = when (status) {
        ErrorStatus.BAD_REQUEST -> HttpStatus.BAD_REQUEST
        ErrorStatus.UNAUTHORIZED -> HttpStatus.UNAUTHORIZED
        ErrorStatus.FORBIDDEN -> HttpStatus.FORBIDDEN
        ErrorStatus.NOT_FOUND -> HttpStatus.NOT_FOUND
        ErrorStatus.CONFLICT -> HttpStatus.CONFLICT
        ErrorStatus.INTERNAL_ERROR -> HttpStatus.INTERNAL_SERVER_ERROR
    }
}
