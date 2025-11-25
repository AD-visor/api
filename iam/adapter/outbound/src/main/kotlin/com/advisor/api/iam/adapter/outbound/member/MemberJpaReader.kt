package com.advisor.api.iam.adapter.outbound.member

import org.springframework.data.jpa.repository.JpaRepository

interface MemberJpaReader: JpaRepository<MemberViewEntity, Long>
