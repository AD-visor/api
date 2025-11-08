package com.advisor.api.subscription.domain.token_usage

import com.advisor.api.common.core.domain.AggregateRoot
import com.advisor.api.common.core.domain.vo.identifier.*
import com.advisor.api.common.exception.CustomException
import com.advisor.api.subscription.domain.token_usage.vo.SourceContext
import java.time.Instant

class TokenUsage private constructor(
    id: TokenUsageId,
    private val props: TokenUsageProps
): AggregateRoot<TokenUsageId>(id) {
    init { validate() }

    companion object {
        fun create(id: TokenUsageId, props: TokenUsageProps): TokenUsage {
            return TokenUsage(id, props)
        }

        fun of(id: TokenUsageId, props: TokenUsageProps): TokenUsage {
            return TokenUsage(id, props)
        }
    }

    private fun validate() {
        require(usedTokens > 0) { CustomException(
            TokenUsageDomainExceptionCode.TOKEN_USAGE_NON_POSITIVE_AMOUNT,
            "[TokenUsage] 사용된 토큰 수는 양수여야 합니다."
        ) }

        require(
            listOf(contentRequestId, contentRevisionId, contentId).count { it != null } == 1
        ) { CustomException(
            TokenUsageDomainExceptionCode.TOKEN_USAGE_INVALID_SOURCE_ID_COMBINATION,
            "[TokenUsage] 토큰 사용처 Id는 정확히 하나만 존재해야 합니다."
        ) }

        require(
            (contentRequestId != null && sourceContext == SourceContext.CONTENT_REQUEST) ||
                    (contentRevisionId != null && sourceContext == SourceContext.CONTENT_REVISION) ||
                    (contentId != null && sourceContext == SourceContext.CONTENT)
        ) { CustomException(
            TokenUsageDomainExceptionCode.TOKEN_USAGE_SOURCE_CONTEXT_MISMATCH,
            "[TokenUsage] 토큰 사용처와 Id type이 일치하지 않습니다."
        ) }

    }

    val memberId: MemberId get() = props.memberId
    val planId: PlanId get() = props.planId
    val subscriptionId: SubscriptionId get() = props.subscriptionId
    val usedTokens: Long get() = props.usedTokens
    val usedAt: Instant get() = props.usedAt
    val sourceContext: SourceContext get() = props.sourceContext
    val contentRequestId: ContentRequestId? get() = props.contentRequestId
    val contentRevisionId: ContentRevisionId? get() = props.contentRevisionId
    val contentId get(): ContentId? = props.contentId
}
