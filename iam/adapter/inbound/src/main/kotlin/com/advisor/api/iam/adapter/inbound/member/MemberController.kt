package com.advisor.api.iam.adapter.inbound.member

import com.advisor.api.common.core.presentation.BaseApiResponse
import com.advisor.api.iam.adapter.inbound.member.dto.request.CreateMemberReqDto
import com.advisor.api.iam.port.inbound.member.command.DeleteMemberCommand
import com.advisor.api.iam.port.inbound.member.usecase.CreateMemberUseCase
import com.advisor.api.iam.port.inbound.member.usecase.DeleteMemberUseCase
import com.advisor.api.iam.port.inbound.member.usecase.GetMemberUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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
    fun deleteMember(): ResponseEntity<BaseApiResponse<Unit>> {
        val command = DeleteMemberCommand(id = 1L) // TODO: 임시로 고정 ID 사용, 추후 인증 정보에서 ID 가져오도록 수정
        deleteMemberUseCase.execute(command)

        val response = BaseApiResponse<Unit>(
            success = true,
            message = "회원 삭제 성공",
            httpStatus = HttpStatus.OK,
        )

        return ResponseEntity.status(HttpStatus.OK).body(response)
    }

    /*
    @GetMapping
    fun getMember(): ResponseEntity<BaseApiResponse<MemberResDto>> {
        val query = GetMemberQuery(id = 1L) // TODO: 임시로 고정 ID 사용, 추후 인증 정보에서 ID 가져오도록 수정
        val result = getMemberUseCase.execute(query)

        val response = BaseApiResponse<MemberResDto>(
            success = true,
            message = "회원 조회 성공",
            httpStatus = HttpStatus.OK,
            data = MemberResDto(
                id = result.id,
                email = result.email,
                createdAt = result.createdAt
            )

            // View model mapping 조금 더 생각해 보기
        )

        return ResponseEntity.status(HttpStatus.OK).body(response)
    }

     */
}
