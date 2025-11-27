package com.advisor.api.subscription.adapter.inbound.plan

import com.advisor.api.common.core.presentation.BaseApiResponse
import com.advisor.api.subscription.port.inbound.plan.UndeletePlanUseCase
import com.advisor.api.subscription.port.inbound.plan.command.UndeletePlanCommand
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/plan")
class PlanController(
    private val undeletePlanUseCase: UndeletePlanUseCase
) {
    @PatchMapping("/undelete/{planId}")
    fun undeletePlan(@PathVariable planId: Long): ResponseEntity<BaseApiResponse<Unit>> {
        val command = UndeletePlanCommand(planId)
        undeletePlanUseCase.execute(command)

        val response = BaseApiResponse<Unit>(
            success = true,
            message = "플랜 복구 성공",
            httpStatus = HttpStatus.OK
        )

        return ResponseEntity.status(HttpStatus.OK).body(response)
    }
}
