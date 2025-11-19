package com.advisor.api.iam.adapter.outbound.member

import com.advisor.api.common.core.domain.vo.identifier.MemberId
import com.advisor.api.common.exception.CustomException
import com.advisor.api.iam.domain.member.Member
import com.advisor.api.iam.domain.member.MemberStore
import org.springframework.stereotype.Repository

@Repository
class MemberStoreImpl(
    private val memberJpaRepository: MemberJpaStore
) : MemberStore {
    override fun save(member: Member) {
        val jpaEntity = MemberEntity.toPersistence(member)
        memberJpaRepository.save(jpaEntity)
    }

    override fun loadById(id: MemberId): Member {
        val jpaEntity = memberJpaRepository.findById(id.value).orElseThrow {
            CustomException(
                MemberInfrastructureExceptionCode.MEMBER_NOT_FOUND,
                "[Member] ${id}에 해당하는 회원이 존재하지 않습니다."
            )
        }

        return jpaEntity.toDomain()
    }

    override fun findById(id: MemberId): Member? {
        return memberJpaRepository.findById(id.value)
            .map { it.toDomain() }
            .orElse(null)
    }
}
