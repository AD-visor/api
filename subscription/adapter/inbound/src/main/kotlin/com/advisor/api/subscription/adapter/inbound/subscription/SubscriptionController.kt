package com.advisor.api.subscription.adapter.inbound.subscription

import com.advisor.api.common.core.presentation.BaseApiResponse
import com.advisor.api.subscription.adapter.inbound.subscription.dto.request.RegisterSubscriptionReqDto
import com.advisor.api.subscription.port.inbound.subscription.CancelSubscriptionUseCase
import com.advisor.api.subscription.port.inbound.subscription.ExpireSubscriptionUseCase
import com.advisor.api.subscription.port.inbound.subscription.RegisterSubscriptionUseCase
import com.advisor.api.subscription.port.inbound.subscription.SuspendSubscriptionUseCase
import com.advisor.api.subscription.port.inbound.subscription.command.UpdateSubscriptionStatusCommand
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/subscription")
class SubscriptionController(
    private val registerSubscriptionUseCase: RegisterSubscriptionUseCase,
    private val cancelSubscriptionUseCase: CancelSubscriptionUseCase,
    private val suspendSubscriptionUseCase: SuspendSubscriptionUseCase,
    private val expireSubscriptionUseCase: ExpireSubscriptionUseCase,
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

    @PatchMapping("/{subscriptionId}/cancel")
    fun cancelSubscription(
        @PathVariable subscriptionId: String
    ): ResponseEntity<BaseApiResponse<Unit>> {
        val command = UpdateSubscriptionStatusCommand(subscriptionId.toLong())
        cancelSubscriptionUseCase.execute(command)

        val apiResponse = BaseApiResponse<Unit>(
            success = true,
            message = "구독 취소 성공",
            httpStatus = HttpStatus.OK,
        )

        return ResponseEntity.status(HttpStatus.OK).body(apiResponse)
    }

    @PatchMapping("/{subscriptionId}/suspend")
    fun suspendSubscription(
        @PathVariable subscriptionId: String
    ): ResponseEntity<BaseApiResponse<Unit>> {
        val command = UpdateSubscriptionStatusCommand(subscriptionId.toLong())
        suspendSubscriptionUseCase.execute(command)

        val apiResponse = BaseApiResponse<Unit>(
            success = true,
            message = "구독 일시정지 성공",
            httpStatus = HttpStatus.OK,
        )

        return ResponseEntity.status(HttpStatus.OK).body(apiResponse)
    }

    @PatchMapping("/{subscriptionId}/expire")
    fun expireSubscription(
        @PathVariable subscriptionId: String
    ): ResponseEntity<BaseApiResponse<Unit>> {
        val command = UpdateSubscriptionStatusCommand(subscriptionId.toLong())
        expireSubscriptionUseCase.execute(command)

        val apiResponse = BaseApiResponse<Unit>(
            success = true,
            message = "구독 만료 성공",
            httpStatus = HttpStatus.OK,
        )

        return ResponseEntity.status(HttpStatus.OK).body(apiResponse)
    }
}
