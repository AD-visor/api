package com.advisor.api.media.adapter.outbound

import org.springframework.data.jpa.repository.JpaRepository

interface MediaJpaStore: JpaRepository<MediaEntity, Long>
