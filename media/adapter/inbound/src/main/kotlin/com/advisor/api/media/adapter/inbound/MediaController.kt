package com.advisor.api.media.adapter.inbound

import com.advisor.api.common.core.presentation.BaseApiResponse
import com.advisor.api.common.core.presentation.CustomUserDetails
import com.advisor.api.media.port.inbound.DeleteMediaUseCase
import com.advisor.api.media.port.inbound.command.DeleteMediaCommand
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/media")
class MediaController(
    private val deleteMediaUseCase: DeleteMediaUseCase
) {
    @DeleteMapping("/{mediaId}")
    fun deleteMedia(
        @AuthenticationPrincipal member: CustomUserDetails,
        @PathVariable mediaId: String
    ): ResponseEntity<BaseApiResponse<Unit>> {
        val command = DeleteMediaCommand(
            mediaId = mediaId.toLong(),
            memberId = member.id
        )

        deleteMediaUseCase.execute(command)

        val apiResponse = BaseApiResponse<Unit>(
            success = true,
            message = "Media deleted successfully",
            httpStatus = HttpStatus.OK
        )

        return ResponseEntity.status(HttpStatus.OK).body(apiResponse)
    }
}
