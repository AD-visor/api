package com.advisor.api.iam.infrastructure.member

import org.springframework.data.jpa.repository.JpaRepository

interface MemberJpaRepository: JpaRepository<MemberEntity, Long>
