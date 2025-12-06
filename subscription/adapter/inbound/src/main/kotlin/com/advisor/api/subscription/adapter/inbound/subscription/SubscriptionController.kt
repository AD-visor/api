package com.advisor.api.subscription.adapter.inbound.subscription

import com.advisor.api.common.core.presentation.BaseApiResponse
import com.advisor.api.common.core.presentation.CustomUserDetails
import com.advisor.api.subscription.adapter.inbound.subscription.dto.request.RegisterSubscriptionReqDto
import com.advisor.api.subscription.port.inbound.subscription.*
import com.advisor.api.subscription.port.inbound.subscription.command.UpdateSubscriptionStatusCommand
import com.advisor.api.subscription.port.inbound.subscription.query.GetMemberSubscriptionQuery
import com.advisor.api.subscription.port.inbound.subscription.result.GetSubscriptionResult
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
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
    private val resumeSubscriptionUseCase: ResumeSubscriptionUseCase,
    private val getMemberSubscriptionUseCase: GetMemberSubscriptionUseCase,
) {
    @PostMapping
    fun registerSubscription(
        @RequestBody dto: RegisterSubscriptionReqDto,
        @AuthenticationPrincipal member: CustomUserDetails,
    ): ResponseEntity<BaseApiResponse<Unit>> {
        val command = dto.toCommand(member.id)

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
        @PathVariable subscriptionId: String,
        @AuthenticationPrincipal member: CustomUserDetails,
    ): ResponseEntity<BaseApiResponse<Unit>> {
        val command = UpdateSubscriptionStatusCommand(
            subscriptionId = subscriptionId.toLong(),
            memberId = member.id
        )
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
        @PathVariable subscriptionId: String,
        @AuthenticationPrincipal member: CustomUserDetails,
    ): ResponseEntity<BaseApiResponse<Unit>> {
        val command = UpdateSubscriptionStatusCommand(
            subscriptionId = subscriptionId.toLong(),
            memberId = member.id
        )
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
        @PathVariable subscriptionId: String,
        @AuthenticationPrincipal member: CustomUserDetails,
    ): ResponseEntity<BaseApiResponse<Unit>> {
        val command = UpdateSubscriptionStatusCommand(
            subscriptionId = subscriptionId.toLong(),
            memberId = member.id
        )
        expireSubscriptionUseCase.execute(command)

        val apiResponse = BaseApiResponse<Unit>(
            success = true,
            message = "구독 만료 성공",
            httpStatus = HttpStatus.OK,
        )

        return ResponseEntity.status(HttpStatus.OK).body(apiResponse)
    }

    @PatchMapping("/{subscriptionId}/resume")
    fun resumeSubscription(
        @PathVariable subscriptionId: String,
        @AuthenticationPrincipal member: CustomUserDetails,
    ): ResponseEntity<BaseApiResponse<Unit>> {
        val command = UpdateSubscriptionStatusCommand(
            subscriptionId = subscriptionId.toLong(),
            memberId = member.id
        )
        resumeSubscriptionUseCase.execute(command)

        val apiResponse = BaseApiResponse<Unit>(
            success = true,
            message = "구독 만료 성공",
            httpStatus = HttpStatus.OK,
        )

        return ResponseEntity.status(HttpStatus.OK).body(apiResponse)
    }

    @GetMapping
    fun getMemberSubscription(
        @AuthenticationPrincipal member: CustomUserDetails,
    ): ResponseEntity<BaseApiResponse<GetSubscriptionResult>> {
        val query = GetMemberSubscriptionQuery(member.id)
        val result = getMemberSubscriptionUseCase.execute(query)

        val apiResponse = BaseApiResponse(
            success = true,
            message = "회원 구독 조회 성공",
            httpStatus = HttpStatus.OK,
            data = result
        )

        return ResponseEntity.status(HttpStatus.OK).body(apiResponse)
    }
}
