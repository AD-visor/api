package com.advisor.api.conversation.application

import com.advisor.api.conversation.domain.conversation.ConversationReader
import com.advisor.api.conversation.port.inbound.RefreshConversationViewUseCase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
class RefreshConversationViewService(
    private val conversationReader: ConversationReader
): RefreshConversationViewUseCase {
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    override fun execute() {
        conversationReader.refreshView()
    }
}
