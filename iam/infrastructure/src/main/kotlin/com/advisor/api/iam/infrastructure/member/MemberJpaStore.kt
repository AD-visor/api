package com.advisor.api.iam.infrastructure.member

import org.springframework.data.jpa.repository.JpaRepository

interface MemberJpaStore: JpaRepository<MemberEntity, Long>
