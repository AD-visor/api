package com.advisor.api.conversation.application

import com.advisor.api.common.core.domain.vo.identifier.ConversationId
import com.advisor.api.common.core.domain.vo.identifier.MemberId
import com.advisor.api.common.core.infrastructure.DomainEventPublisher
import com.advisor.api.conversation.port.outbound.ConversationStore
import com.advisor.api.conversation.port.inbound.DeleteConversationUseCase
import com.advisor.api.conversation.port.inbound.command.DeleteConversationCommand
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DeleteConversationService(
    private val conversationStore: ConversationStore,
    private val domainEventPublisher: DomainEventPublisher
): DeleteConversationUseCase {
    @Transactional
    override fun execute(command: DeleteConversationCommand) {
        val conversation = conversationStore.loadByIdAndMemberId(
            id = ConversationId(command.id),
            memberId = MemberId(command.memberId)
        )

        val updatedConversation = conversation.delete()

        conversationStore.delete(conversation.id)

        domainEventPublisher.publishFrom(updatedConversation)
    }
}
