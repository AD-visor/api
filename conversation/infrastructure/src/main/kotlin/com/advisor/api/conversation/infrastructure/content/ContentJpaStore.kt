package com.advisor.api.conversation.infrastructure.content

import org.springframework.data.jpa.repository.JpaRepository

interface ContentJpaStore: JpaRepository<ContentEntity, Long>
