package com.advisor.api.conversation.application

import com.advisor.api.common.core.domain.vo.identifier.ConversationId
import com.advisor.api.common.core.domain.vo.identifier.MemberId
import com.advisor.api.common.core.infrastructure.DomainEventPublisher
import com.advisor.api.conversation.port.outbound.ConversationStore
import com.advisor.api.conversation.port.inbound.command.ArchiveConversationCommand
import com.advisor.api.conversation.port.inbound.ArchiveConversationUseCase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ArchiveConversationService(
    private val conversationStore: ConversationStore,
    private val domainEventPublisher: DomainEventPublisher
): ArchiveConversationUseCase {
    @Transactional
    override fun execute(command: ArchiveConversationCommand) {
        val conversation = conversationStore.loadByIdAndMemberId(
            id = ConversationId(command.id),
            memberId = MemberId(command.memberId),
            archived = false
        )

        val updatedConversation = conversation.archive()

        conversationStore.save(updatedConversation)

        domainEventPublisher.publishFrom(updatedConversation)
    }
}
