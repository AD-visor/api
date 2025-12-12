package com.advisor.api.conversation.adapter.outbound.conversation

import com.advisor.api.conversation.adapter.outbound.conversation.projection.ConversationMetadataProjection
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.Optional

interface ConversationJpaReader: JpaRepository<ConversationViewEntity, Long> {
    @Query(
        """
            SELECT 
                c.id as id,
                c.productName as productName,
                c.updatedAt as updatedAt
            FROM ConversationViewEntity c
            WHERE c.memberId = :memberId
            ORDER BY c.updatedAt DESC
        """
    )
    fun findAllMetadataByMemberId(
        @Param("memberId") memberId: Long
    ): List<ConversationMetadataProjection>
}
