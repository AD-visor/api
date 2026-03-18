package com.advisor.api.iam.adapter.inbound.member

import com.advisor.api.common.core.presentation.BaseApiResponse
import com.advisor.api.common.core.presentation.CookieManager
import com.advisor.api.common.core.presentation.CustomUserDetails
import com.advisor.api.iam.adapter.inbound.member.dto.request.CreateMemberReqDto
import com.advisor.api.iam.port.inbound.member.CreateMemberUseCase
import com.advisor.api.iam.port.inbound.member.command.WithdrawMemberCommand
import com.advisor.api.iam.port.inbound.member.query.GetMemberQuery
import com.advisor.api.iam.port.inbound.member.GetMemberUseCase
import com.advisor.api.iam.port.inbound.member.WithdrawMemberUseCase
import com.advisor.api.iam.port.inbound.member.view.MemberView
import jakarta.servlet.http.HttpServletResponse
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
    private val withdrawMemberUseCase: WithdrawMemberUseCase,
    private val getMemberUseCase: GetMemberUseCase,
    private val cookieManager: CookieManager
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
    fun withdrawMember(
        @AuthenticationPrincipal member: CustomUserDetails,
        response: HttpServletResponse
    ): ResponseEntity<BaseApiResponse<Unit>> {
        val command = WithdrawMemberCommand(member.id)
        withdrawMemberUseCase.execute(command)

        cookieManager.deleteCookie(response, "accessToken")
        cookieManager.deleteCookie(response, "refreshToken")

        val apiResponse = BaseApiResponse<Unit>(
            success = true,
            message = "회원 삭제 성공",
            httpStatus = HttpStatus.OK,
        )

        return ResponseEntity.status(HttpStatus.OK).body(apiResponse)
    }

    @GetMapping("/me")
    fun getMember(@AuthenticationPrincipal member: CustomUserDetails): ResponseEntity<BaseApiResponse<MemberView>> {
        val query = GetMemberQuery(member.id)
        val result = getMemberUseCase.execute(query)

        val response = BaseApiResponse<MemberView>(
            success = true,
            message = "회원 조회 성공",
            httpStatus = HttpStatus.OK,
            data = result
        )

        return ResponseEntity.status(HttpStatus.OK).body(response)
    }
}
