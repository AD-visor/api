package com.advisor.api.media.application

import com.advisor.api.common.core.domain.vo.identifier.ConversationId
import com.advisor.api.common.core.domain.vo.identifier.ConversationMessageId
import com.advisor.api.common.core.domain.vo.identifier.MediaId
import com.advisor.api.common.core.domain.vo.identifier.MemberId
import com.advisor.api.common.core.infrastructure.SnowFlakeIdUtil
import com.advisor.api.media.domain.Media
import com.advisor.api.media.domain.MediaProps
import com.advisor.api.media.domain.MediaStore
import com.advisor.api.media.domain.vo.MediaPath
import com.advisor.api.media.domain.vo.MediaType
import com.advisor.api.media.domain.vo.MimeType
import com.advisor.api.media.port.inbound.CreateMediaUseCase
import com.advisor.api.media.port.inbound.command.CreateMediaCommand
import com.advisor.api.media.port.outbound.FileStoragePort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class CreateMediaService(
    private val mediaStore: MediaStore,
    private val fileStoragePort: FileStoragePort,
    private val snowFlakeIdUtil: SnowFlakeIdUtil
): CreateMediaUseCase {
    @Transactional
    override fun execute(commands: List<CreateMediaCommand>) {
        val medias = commands.map { createNewMedia(it) }
        mediaStore.saveAll(medias)
    }

    private fun createNewMedia(command: CreateMediaCommand): Media {
        val path = fileStoragePort.save(
            name = "ad_${command.conversationId}_${UUID.randomUUID()}.webp",
            bytes = command.file
        )

        val mediaProps = MediaProps(
            path = MediaPath.create(path),
            mediaType = MediaType.create(MediaType.IMAGE),
            mimeType = MimeType.create(command.mimeType),
            conversationId = ConversationId(command.conversationId),
            conversationMessageId = ConversationMessageId(command.conversationMessageId),
            memberId = MemberId(command.memberId),
            width = command.width,
            height = command.height,
            fileSize = command.fileSize
        )

        return Media.create(MediaId(snowFlakeIdUtil.generateId().toLong()), mediaProps)
    }
}
