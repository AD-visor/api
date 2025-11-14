package com.advisor.api.conversation.domain.conversation.vo

import com.advisor.api.common.exception.CustomException
import com.advisor.api.conversation.domain.conversation.ConversationDomainExceptionCode

class ToneStyle private constructor(val value: String) {
    init { validate() }

    companion object {
        val FRIENDLY = ToneStyle("FRIENDLY")
        val PROFESSIONAL = ToneStyle("PROFESSIONAL")
        val LUXURY = ToneStyle("LUXURY")
        val CASUAL = ToneStyle("CASUAL")
        val EMOTIONAL = ToneStyle("EMOTIONAL")
        val HUMOROUS = ToneStyle("HUMOROUS")

        fun create(value: String): ToneStyle {
            return when (value) {
                "FRIENDLY" -> FRIENDLY
                "PROFESSIONAL" -> PROFESSIONAL
                "LUXURY" -> LUXURY
                "CASUAL" -> CASUAL
                "EMOTIONAL" -> EMOTIONAL
                "HUMOROUS" -> HUMOROUS
                else -> throw CustomException(
                    ConversationDomainExceptionCode.CONVERSATION_TONE_STYLE_INVALID,
                    "[Conversation] 유효하지 않은 톤앤스타일입니다."
                )
            }
        }
    }

    private fun validate() {}

    override fun equals(other: Any?): Boolean = other is ToneStyle && this.value == other.value
    override fun hashCode(): Int = value.hashCode()
}
