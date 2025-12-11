package com.advisor.api.conversation.application

import com.advisor.api.conversation.domain.conversation.ConversationReader
import com.advisor.api.conversation.port.inbound.RefreshConversationMessageViewUseCase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
class RefreshConversationMessageViewService(
    private val conversationReader: ConversationReader
): RefreshConversationMessageViewUseCase {
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    override fun execute() {
        conversationReader.refreshMessageView()
    }
}
