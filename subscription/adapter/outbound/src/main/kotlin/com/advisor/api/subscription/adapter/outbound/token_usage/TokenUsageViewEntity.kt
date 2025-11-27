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
    val sourceContext: String,

    @Column
    val contentRequestId: Long?,

    @Column
    val contentRevisionId: Long?,

    @Column
    val contentId: Long?
) {
    fun toModel(): TokenUsageView {
        return TokenUsageView(
            id = this.id,
            memberId = this.memberId,
            subscriptionId = this.subscriptionId,
            usedTokens = this.usedTokens,
            usedAt = this.usedAt.toEpochMilli(),
            sourceContext = this.sourceContext,
            contentRequestId = this.contentRequestId,
            contentRevisionId = this.contentRevisionId,
            contentId = this.contentId
        )
    }
}
