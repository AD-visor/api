package com.advisor.api.token_usage.adapter.inbound

import com.advisor.api.common.core.presentation.BaseApiResponse
import com.advisor.api.common.core.presentation.CustomUserDetails
import com.advisor.api.token_usage.port.inbound.GetDailyTokenUsageUseCase
import com.advisor.api.token_usage.port.inbound.GetMonthlyTokenUsageUseCase
import com.advisor.api.token_usage.port.inbound.GetTokenUsageListUseCase
import com.advisor.api.token_usage.port.inbound.RecordTokenUsageUseCase
import com.advisor.api.token_usage.port.inbound.command.RecordTokenUsageCommand
import com.advisor.api.token_usage.port.inbound.query.GetDailyTokenUsageQuery
import com.advisor.api.token_usage.port.inbound.query.GetMonthlyTokenUsageQuery
import com.advisor.api.token_usage.port.inbound.query.GetTokenUsageListQuery
import com.advisor.api.token_usage.port.inbound.view.DailyTokenUsageView
import com.advisor.api.token_usage.port.inbound.view.MonthlyTokenUsageView
import com.advisor.api.token_usage.port.inbound.view.TokenUsageView
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.Instant
import java.time.YearMonth
import java.time.ZoneOffset

@RestController
@RequestMapping("/token-usage")
class TokenUsageController(
    private val getTokenUsageListUseCase: GetTokenUsageListUseCase,
    private val getMonthlyTokenUsageUseCase: GetMonthlyTokenUsageUseCase,
    private val getDailyTokenUsageUseCase: GetDailyTokenUsageUseCase,
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

    @GetMapping("/monthly")
    fun getMonthlyTokenUsage(
        @AuthenticationPrincipal member: CustomUserDetails,
        @RequestParam(required = false) subscriptionId: Long?,
        @RequestParam(required = false) year: Int?,
        @RequestParam(required = false) month: Int?
    ): ResponseEntity<BaseApiResponse<List<MonthlyTokenUsageView>>> {
        val targetMonth = if (year != null && month != null) {
            YearMonth.of(year, month)
        } else {
            YearMonth.now()
        }

        val query = GetMonthlyTokenUsageQuery(
            subscriptionId = subscriptionId,
            memberId = member.id,
            month = targetMonth
        )

        val result = getMonthlyTokenUsageUseCase.execute(query)

        return ResponseEntity.ok(
            BaseApiResponse(
                success = true,
                message = "월별 토큰 사용량 조회 성공",
                data = result,
                httpStatus = HttpStatus.OK
            )
        )
    }

    @GetMapping("/daily")
    fun getDailyTokenUsage(
        @AuthenticationPrincipal member: CustomUserDetails,
        @RequestParam subscriptionId: Long,
        @RequestParam(required = false) from: Instant?,
        @RequestParam(required = false) to: Instant?
    ): ResponseEntity<BaseApiResponse<List<DailyTokenUsageView>>> {
        val query = GetDailyTokenUsageQuery(
            memberId = member.id,
            subscriptionId = subscriptionId,
            from = from ?: YearMonth.now()
                .atDay(1)
                .atStartOfDay(ZoneOffset.UTC)
                .toInstant(),
            to = to ?: YearMonth.now()
                .atEndOfMonth()
                .atTime(23, 59, 59)
                .toInstant(ZoneOffset.UTC)
        )

        val result = getDailyTokenUsageUseCase.execute(query)

        return ResponseEntity.ok(
            BaseApiResponse(
                success = true,
                message = "일별 토큰 사용량 조회 성공",
                data = result,
                httpStatus = HttpStatus.OK
            )
        )
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
