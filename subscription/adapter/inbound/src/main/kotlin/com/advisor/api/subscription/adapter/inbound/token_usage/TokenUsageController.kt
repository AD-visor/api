package com.advisor.api.subscription.adapter.inbound.token_usage

import com.advisor.api.common.core.presentation.BaseApiResponse
import com.advisor.api.subscription.port.inbound.token_usage.GetTokenUsageListUseCase
import com.advisor.api.subscription.port.inbound.token_usage.query.GetTokenUsageListQuery
import com.advisor.api.subscription.port.inbound.token_usage.result.GetTokenUsageResult
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/token-usage")
class TokenUsageController(
    private val getTokenUsageListUseCase: GetTokenUsageListUseCase
) {
    @GetMapping
    fun getTokenUsage(): ResponseEntity<BaseApiResponse<List<GetTokenUsageResult>>> {
        val query = GetTokenUsageListQuery(
            memberId = 1L,
            startAt = null,
            endAt = null
        )

        val result = getTokenUsageListUseCase.execute(query)

        val apiResponse = BaseApiResponse<List<GetTokenUsageResult>>(
            success = true,
            message = "토큰 사용량 조회 성공",
            data = result,
            httpStatus = HttpStatus.OK,
        )

        return ResponseEntity.status(HttpStatus.OK).body(apiResponse)
    }
}
