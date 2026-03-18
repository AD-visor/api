package com.advisor.api.conversation.application.refresher

import com.advisor.api.conversation.port.outbound.ConversationReader
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
internal class RefreshConversationViewService(
    private val conversationReader: ConversationReader
) {
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun execute() {
        conversationReader.refreshView()
    }
}
