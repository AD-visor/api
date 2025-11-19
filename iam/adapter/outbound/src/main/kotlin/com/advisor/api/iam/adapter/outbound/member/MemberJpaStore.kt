package com.advisor.api.iam.adapter.outbound.member

import org.springframework.data.jpa.repository.JpaRepository

interface MemberJpaStore: JpaRepository<MemberEntity, Long>
