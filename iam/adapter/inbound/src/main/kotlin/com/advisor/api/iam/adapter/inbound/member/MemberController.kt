package com.advisor.api.iam.adapter.inbound.member

import com.advisor.api.common.core.presentation.BaseApiResponse
import com.advisor.api.common.core.presentation.CustomUserDetails
import com.advisor.api.iam.adapter.inbound.member.dto.request.CreateMemberReqDto
import com.advisor.api.iam.adapter.inbound.member.dto.response.MemberResDto
import com.advisor.api.iam.port.inbound.member.command.DeleteMemberCommand
import com.advisor.api.iam.port.inbound.member.query.GetMemberQuery
import com.advisor.api.iam.port.inbound.member.usecase.CreateMemberUseCase
import com.advisor.api.iam.port.inbound.member.usecase.DeleteMemberUseCase
import com.advisor.api.iam.port.inbound.member.usecase.GetMemberUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/member")
class MemberController(
    private val createMemberUseCase: CreateMemberUseCase,
    private val deleteMemberUseCase: DeleteMemberUseCase,
    private val getMemberUseCase: GetMemberUseCase,
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

    @PostMapping("/withdraw")
    fun deleteMember(@AuthenticationPrincipal member: CustomUserDetails): ResponseEntity<BaseApiResponse<Unit>> {
        val command = DeleteMemberCommand(member.id)
        deleteMemberUseCase.execute(command)

        val response = BaseApiResponse<Unit>(
            success = true,
            message = "회원 삭제 성공",
            httpStatus = HttpStatus.OK,
        )

        return ResponseEntity.status(HttpStatus.OK).body(response)
    }

    @GetMapping("/me")
    fun getMember(@AuthenticationPrincipal member: CustomUserDetails): ResponseEntity<BaseApiResponse<MemberResDto>> {
        val query = GetMemberQuery(member.id)
        val result = getMemberUseCase.execute(query)

        val response = BaseApiResponse<MemberResDto>(
            success = true,
            message = "회원 조회 성공",
            httpStatus = HttpStatus.OK,
            data = MemberResDto.fromResult(result)

            // View model mapping 조금 더 생각해 보기
        )

        return ResponseEntity.status(HttpStatus.OK).body(response)
    }
}
