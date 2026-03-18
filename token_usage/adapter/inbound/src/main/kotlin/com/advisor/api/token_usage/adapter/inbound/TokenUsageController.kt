package com.advisor.api.token_usage.adapter.inbound

import com.advisor.api.common.core.presentation.BaseApiResponse
import com.advisor.api.common.core.presentation.CustomUserDetails
import com.advisor.api.token_usage.port.inbound.GetTokenUsageListUseCase
import com.advisor.api.token_usage.port.inbound.RecordTokenUsageUseCase
import com.advisor.api.token_usage.port.inbound.command.RecordTokenUsageCommand
import com.advisor.api.token_usage.port.inbound.query.GetTokenUsageListQuery
import com.advisor.api.token_usage.port.inbound.view.TokenUsageView
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/token-usage")
class TokenUsageController(
    private val getTokenUsageListUseCase: GetTokenUsageListUseCase,
    // 임시
    private val recordTokenUsageUseCase: RecordTokenUsageUseCase
) {
    @GetMapping
    fun getTokenUsage(
        @AuthenticationPrincipal member: CustomUserDetails
    ): ResponseEntity<BaseApiResponse<List<TokenUsageView>>> {
        val query = GetTokenUsageListQuery(
            memberId = member.id,
            startAt = null,
            endAt = null
        )

        val result = getTokenUsageListUseCase.execute(query)

        val apiResponse = BaseApiResponse<List<TokenUsageView>>(
            success = true,
            message = "토큰 사용량 조회 성공",
            data = result,
            httpStatus = HttpStatus.OK,
        )

        return ResponseEntity.status(HttpStatus.OK).body(apiResponse)
    }

    // 임시
    @PostMapping
    fun recordTokenUsage(
        @AuthenticationPrincipal member: CustomUserDetails
    ) : ResponseEntity<BaseApiResponse<Unit>> {
        val command = RecordTokenUsageCommand(
            memberId = member.id,
            subscriptionId = 1L,
            planId = 1L,
            usedTokens = 10,
            conversationMessageId = 1L,
        )
        recordTokenUsageUseCase.execute(command)

        val apiResponse = BaseApiResponse<Unit>(
            success = true,
            message = "토큰 사용량 기록 성공",
            data = null,
            httpStatus = HttpStatus.CREATED,
        )

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse)
    }
}
