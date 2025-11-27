package com.advisor.api.conversation.adapter.outbound.content

import org.springframework.data.jpa.repository.JpaRepository

interface ContentJpaStore: JpaRepository<ContentEntity, Long>
