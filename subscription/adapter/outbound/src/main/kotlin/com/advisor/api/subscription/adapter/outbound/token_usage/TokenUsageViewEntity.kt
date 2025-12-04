package com.advisor.api.subscription.adapter.outbound.token_usage

import com.advisor.api.subscription.domain.token_usage.TokenUsageView
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.annotation.Immutable
import java.time.Instant

@Entity
@Immutable
@Table(name = "vw_token_usage")
class TokenUsageViewEntity(
    @Id
    val id: Long,

    @Column(nullable = false)
    val memberId: Long,

    @Column(nullable = false)
    val subscriptionId: Long,

    @Column(nullable = false)
    val usedTokens: Long,

    @Column(nullable = false)
    val usedAt: Instant,

    @Column(nullable = false)
    val conversationMessageId: Long,
) {
    fun toModel(): TokenUsageView {
        return TokenUsageView(
            id = id,
            memberId = memberId,
            subscriptionId = subscriptionId,
            usedTokens = usedTokens,
            usedAt = usedAt,
            conversationMessageId = conversationMessageId
        )
    }
}
