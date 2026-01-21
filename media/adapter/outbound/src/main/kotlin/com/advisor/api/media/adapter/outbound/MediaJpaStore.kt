package com.advisor.api.media.adapter.outbound

import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface MediaJpaStore: JpaRepository<MediaEntity, Long> {
    fun deleteByIdAndMemberId(id: Long, memberId: Long)
    fun findByIdAndMemberId(id: Long, memberId: Long): Optional<MediaEntity>
}
