package com.advisor.api.common.exception

import com.advisor.api.common.core.presentation.BaseApiResponse
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {
    private val log = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @ExceptionHandler(CustomException::class)
    fun handleCustomException(ex: CustomException): ResponseEntity<BaseApiResponse<Any>> {
        val customExceptionCode = ex.code
        val httpStatus = ErrorStatusMapper.toHttpStatus(customExceptionCode.status)

        log.error("CustomException: {} - {}", customExceptionCode.code, customExceptionCode.message)
        val apiResponse = BaseApiResponse<Any>(
            success = false,
            message = customExceptionCode.message,
            data = ex.data,
            httpStatus = httpStatus,
            customErrorCode = customExceptionCode.code
        )
        return ResponseEntity.badRequest().body(apiResponse)
    }

    @ExceptionHandler(RuntimeException::class)
    fun handleRuntimeException(ex: RuntimeException): ResponseEntity<BaseApiResponse<Unit>> {
        val apiResponse = BaseApiResponse<Unit>(
            success = false,
            message = "${ex.message}",
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR
        )
        return ResponseEntity(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgument(ex: IllegalArgumentException): ResponseEntity<BaseApiResponse<Unit>> {
        val apiResponse = BaseApiResponse<Unit>(
            success = false,
            message = "${ex.message}",
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR
        )
        return ResponseEntity(apiResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(AccessDeniedException::class)
    fun handleAccessDenied(ex: AccessDeniedException): ResponseEntity<BaseApiResponse<Unit>> {
        val apiResponse = BaseApiResponse<Unit>(
            success = false,
            message = "${ex.message}",
            httpStatus = HttpStatus.UNAUTHORIZED
        )
        return ResponseEntity(apiResponse, HttpStatus.UNAUTHORIZED)
    }
}
