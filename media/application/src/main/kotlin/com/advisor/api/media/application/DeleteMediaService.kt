package com.advisor.api.media.application

import com.advisor.api.common.core.domain.vo.identifier.MediaId
import com.advisor.api.common.core.domain.vo.identifier.MemberId
import com.advisor.api.media.port.outbound.MediaStore
import com.advisor.api.media.port.inbound.DeleteMediaUseCase
import com.advisor.api.media.port.inbound.command.DeleteMediaCommand
import com.advisor.api.media.port.outbound.FileStoragePort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DeleteMediaService(
    private val mediaStore: MediaStore,
    private val fileStoragePort: FileStoragePort
): DeleteMediaUseCase {
    @Transactional
    override fun execute(command: DeleteMediaCommand) {
        val media = mediaStore.loadByIdAndMemberId(
            id = MediaId(command.mediaId),
            memberId = MemberId(command.memberId)
        )

        fileStoragePort.delete(media.path.value)

        mediaStore.deleteByIdAndMemberId(media.id, media.memberId)
    }
}
