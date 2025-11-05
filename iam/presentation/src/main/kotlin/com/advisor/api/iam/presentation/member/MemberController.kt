package com.advisor.api.iam.presentation.member

import com.advisor.api.common.core.presentation.BaseApiResponse
import com.advisor.api.iam.application.member.usecase.CreateMemberUseCase
import com.advisor.api.iam.presentation.member.dto.request.CreateMemberReqDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/member")
class MemberController(
    private val createMemberUseCase: CreateMemberUseCase
) {
    @PostMapping
    fun createMember(@RequestBody dto: CreateMemberReqDto): ResponseEntity<BaseApiResponse<Unit>> {
        val command = dto.toCommand()
        createMemberUseCase.execute(command)

        val response = BaseApiResponse<Unit>(
            success = true,
            message = "회원 생성 성공",
            httpStatus = HttpStatus.CREATED,
        )

        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }
}
