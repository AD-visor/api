package com.advisor.api.common.core.domain.event

enum class EventType(val value: String) {
    AI_RESPONSE_GENERATED("ai.response.generated.v1"),
    UNKNOWN("unknown");

    companion object {
        private val lookup = entries.associateBy { it.value }

        fun fromValue(value: String): EventType =
            lookup[value] ?: UNKNOWN
    }
}
