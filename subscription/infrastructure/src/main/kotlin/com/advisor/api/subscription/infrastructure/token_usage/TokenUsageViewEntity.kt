package com.advisor.api.subscription.infrastructure.token_usage

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
    companion object {
        fun fromModel(model: TokenUsageView): TokenUsageViewEntity {
            return TokenUsageViewEntity(
                id = model.id,
                memberId = model.memberId,
                subscriptionId = model.subscriptionId,
                usedTokens = model.usedTokens,
                usedAt = Instant.ofEpochMilli(model.usedAt),
                sourceContext = model.sourceContext,
                contentRequestId = model.contentRequestId,
                contentRevisionId = model.contentRevisionId,
                contentId = model.contentId
            )
        }
    }

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
