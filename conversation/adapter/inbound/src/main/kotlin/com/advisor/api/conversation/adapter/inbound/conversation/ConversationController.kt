package com.advisor.api.conversation.adapter.inbound.conversation

import com.advisor.api.common.core.presentation.BaseApiResponse
import com.advisor.api.common.core.presentation.CustomUserDetails
import com.advisor.api.conversation.adapter.inbound.conversation.dto.request.CreateConversationReqDto
import com.advisor.api.conversation.adapter.inbound.conversation.dto.request.UpdateConversationReqDto
import com.advisor.api.conversation.adapter.inbound.conversation.dto.response.CreateConversationResDto
import com.advisor.api.conversation.port.inbound.CreateConversationUseCase
import com.advisor.api.conversation.port.inbound.UpdateConversationUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/conversation")
class ConversationController(
    private val createConversationUseCase: CreateConversationUseCase,
    private val updateConversationUseCase: UpdateConversationUseCase
) {
    @PostMapping
    fun createConversation(
        @AuthenticationPrincipal member: CustomUserDetails,
        @RequestBody dto: CreateConversationReqDto
    ): ResponseEntity<BaseApiResponse<CreateConversationResDto>> {
        val command = dto.toCommand(member.id)

        val result = createConversationUseCase.execute(command)

        val apiResponse = BaseApiResponse<CreateConversationResDto>(
            success = true,
            message = "대화 생성 성공",
            data = CreateConversationResDto.fromResult(result.id),
            httpStatus = HttpStatus.CREATED
        )

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse)
    }

    @PatchMapping("/{conversationId}")
    fun updateConversation(
        @AuthenticationPrincipal member: CustomUserDetails,
        @RequestBody dto: UpdateConversationReqDto,
        @PathVariable("conversationId") conversationId: String
    ): ResponseEntity<BaseApiResponse<Unit>> {
        val command = dto.toCommand(
            id = conversationId.toLong(),
            memberId = member.id
        )

        updateConversationUseCase.execute(command)

        val apiResponse = BaseApiResponse<Unit>(
            success = true,
            message = "대화 수정 성공",
            data = null,
            httpStatus = HttpStatus.OK
        )

        return ResponseEntity.status(HttpStatus.OK).body(apiResponse)
    }
}
