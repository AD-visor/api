package com.advisor.api.subscription.adapter.inbound.plan

import com.advisor.api.common.core.presentation.BaseApiResponse
import com.advisor.api.subscription.adapter.inbound.plan.dto.request.CreatePlanReqDto
import com.advisor.api.subscription.adapter.inbound.plan.dto.request.UpdatePlanReqDto
import com.advisor.api.subscription.adapter.inbound.plan.dto.response.PlanResDto
import com.advisor.api.subscription.port.inbound.plan.*
import com.advisor.api.subscription.port.inbound.plan.command.DeletePlanCommand
import com.advisor.api.subscription.port.inbound.plan.command.UndeletePlanCommand
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/plan")
class PlanController(
    private val createPlanUseCase: CreatePlanUseCase,
    private val undeletePlanUseCase: UndeletePlanUseCase,
    private val updatePlanUseCase: UpdatePlanUseCase,
    private val deletePlanUseCase: DeletePlanUseCase,
    private val getPlanListUseCase: GetPlanListUseCase
) {
    @PostMapping
    fun createPlan(@RequestBody dto: CreatePlanReqDto): ResponseEntity<BaseApiResponse<Unit>> {
        createPlanUseCase.execute(dto.toCommand())

        val response = BaseApiResponse<Unit>(
            success = true,
            message = "플랜 생성 성공",
            httpStatus = HttpStatus.CREATED
        )

        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @GetMapping
    fun getPlans(): ResponseEntity<BaseApiResponse<List<PlanResDto>>> {
        val results = getPlanListUseCase.execute()

        val response = BaseApiResponse<List<PlanResDto>>(
            success = true,
            message = "플랜 조회 성공",
            httpStatus = HttpStatus.OK,
            data = results.map { PlanResDto.fromResult(it) }
        )

        return ResponseEntity.status(HttpStatus.OK).body(response)
    }

    @PatchMapping("{planId}/undelete")
    fun undeletePlan(@PathVariable planId: String): ResponseEntity<BaseApiResponse<Unit>> {
        val command = UndeletePlanCommand(planId.toLong())
        undeletePlanUseCase.execute(command)

        val response = BaseApiResponse<Unit>(
            success = true,
            message = "플랜 복구 성공",
            httpStatus = HttpStatus.OK
        )

        return ResponseEntity.status(HttpStatus.OK).body(response)
    }

    @PatchMapping("/{planId}")
    fun updatePlan(
        @PathVariable planId: String,
        @RequestBody dto: UpdatePlanReqDto
    ): ResponseEntity<BaseApiResponse<Unit>> {
        val command = dto.toCommand(planId.toLong())
        updatePlanUseCase.execute(command)

        val response = BaseApiResponse<Unit>(
            success = true,
            message = "플랜 수정 성공",
            httpStatus = HttpStatus.OK
        )

        return ResponseEntity.status(HttpStatus.OK).body(response)
    }

    @DeleteMapping("/{planId}")
    fun deletePlan(@PathVariable planId: String): ResponseEntity<BaseApiResponse<Unit>> {
        val command = DeletePlanCommand(planId.toLong())
        deletePlanUseCase.execute(command)

        val response = BaseApiResponse<Unit>(
            success = true,
            message = "플랜 삭제 성공",
            httpStatus = HttpStatus.OK
        )

        return ResponseEntity.status(HttpStatus.OK).body(response)
    }
}
