package com.advisor.api.common.core.domain.event

enum class EventChannel {
    /** Outbox → Debezium → Kafka → 외부 도메인 */
    KAFKA,
    /** Spring ApplicationEvent → 같은 서버 내 리스너 */
    INTERNAL,
    /** 두 채널 모두 발행 */
    BOTH
}