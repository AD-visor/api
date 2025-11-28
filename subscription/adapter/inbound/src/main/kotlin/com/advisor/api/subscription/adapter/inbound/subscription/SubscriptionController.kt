package com.advisor.api.subscription.adapter.inbound.subscription

import com.advisor.api.common.core.presentation.BaseApiResponse
import com.advisor.api.subscription.adapter.inbound.subscription.dto.request.RegisterSubscriptionReqDto
import com.advisor.api.subscription.port.inbound.subscription.RegisterSubscriptionUseCase
import com.advisor.api.subscription.port.inbound.subscription.command.RegisterSubscriptionCommand
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/subscription")
class SubscriptionController(
    private val registerSubscriptionUseCase: RegisterSubscriptionUseCase
) {
    @PostMapping
    fun registerSubscription(
        @RequestBody dto: RegisterSubscriptionReqDto
    ): ResponseEntity<BaseApiResponse<Unit>> {
        val command = dto.toCommand(1L) // TODO: 회원 ID 추후 수정

        registerSubscriptionUseCase.execute(command)

        val apiResponse = BaseApiResponse<Unit>(
            success = true,
            message = "구독 성공",
            httpStatus = HttpStatus.CREATED,
        )

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse)
    }
}
