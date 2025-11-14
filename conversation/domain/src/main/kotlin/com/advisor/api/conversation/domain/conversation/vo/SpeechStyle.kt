package com.advisor.api.conversation.domain.conversation.vo

import com.advisor.api.common.exception.CustomException
import com.advisor.api.conversation.domain.conversation.ConversationDomainExceptionCode

class SpeechStyle private constructor (val value: String) {
    init { validate() }

    companion object {
        val FORMAL = SpeechStyle("FORMAL")
        val INFORMAL = SpeechStyle("INFORMAL")
        val PERSUASIVE = SpeechStyle("PERSUASIVE")
        val STORYTELLING = SpeechStyle("STORYTELLING")
        val DESCRIPTIVE = SpeechStyle("DESCRIPTIVE")

        fun create(value: String): SpeechStyle {
            return when (value) {
                "FORMAL" -> FORMAL
                "INFORMAL" -> INFORMAL
                "PERSUASIVE" -> PERSUASIVE
                "STORYTELLING" -> STORYTELLING
                "DESCRIPTIVE" -> DESCRIPTIVE
                else -> throw CustomException(
                    ConversationDomainExceptionCode.CONVERSATION_SPEECH_STYLE_INVALID,
                    "[Conversation] 유효하지 않은 스피치스타일입니다."
                )
            }
        }
    }

    private fun validate() {}

    override fun equals(other: Any?): Boolean = other is SpeechStyle && this.value == other.value
    override fun hashCode(): Int = value.hashCode()
}
