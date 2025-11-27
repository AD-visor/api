package com.advisor.api.subscription.adapter.outbound.token_usage

import com.advisor.api.common.core.domain.vo.identifier.*
import com.advisor.api.subscription.domain.token_usage.TokenUsage
import com.advisor.api.subscription.domain.token_usage.TokenUsageProps
import com.advisor.api.subscription.domain.token_usage.vo.SourceContext
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.Instant

@Entity
@Table(name = "token_usage")
class TokenUsageEntity(
    @Id
    val id: Long,

    @Column(nullable = false)
    val memberId: Long,

    @Column(nullable = false)
    val planId: Long,

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
        fun fromDomain(domain: TokenUsage): TokenUsageEntity {
            return TokenUsageEntity(
                id = domain.id.value,
                memberId = domain.memberId.value,
                planId = domain.planId.value,
                subscriptionId = domain.subscriptionId.value,
                usedTokens = domain.usedTokens,
                usedAt = domain.usedAt,
                sourceContext = domain.sourceContext.value,
                contentRequestId = domain.contentRequestId?.value,
                contentRevisionId = domain.contentRevisionId?.value,
                contentId = domain.contentId?.value
            )
        }
    }

    fun toDomain(): TokenUsage {
        val tokenUsageProps = TokenUsageProps(
            memberId = MemberId(memberId),
            planId = PlanId(planId),
            subscriptionId = SubscriptionId(subscriptionId),
            usedTokens = usedTokens,
            usedAt = usedAt,
            sourceContext = SourceContext.create(sourceContext),
            contentRequestId = contentRequestId?.let { ContentRequestId(it) },
            contentRevisionId = contentRevisionId?.let { ContentRevisionId(it) },
            contentId = contentId?.let { ContentId(it) }
        )

        return TokenUsage.of(TokenUsageId(id), tokenUsageProps)
    }
}
