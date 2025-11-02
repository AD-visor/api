package com.advisor.iam.infrastructure.member

import com.advisor.api.common.core.domain.vo.identifier.MemberId
import com.advisor.api.common.exception.CustomException
import com.advisor.iam.domain.member.Member
import com.advisor.iam.domain.member.MemberStore
import org.springframework.stereotype.Repository

@Repository
class MemberStoreImpl(
    private val memberJpaRepository: MemberJpaRepository
): MemberStore {
    override fun save(member: Member) {
        val jpaEntity = MemberEntity.toPersistence(member)
        memberJpaRepository.save(jpaEntity)
    }

    override fun load(id: MemberId): Member {
        val jpaEntity = memberJpaRepository.findById(id.value).orElseThrow{ CustomException(
            MemberInfrastructureExceptionCode.MEMBER_NOT_FOUND,
            "[Member] ${id}에 해당하는 회원이 존재하지 않습니다."
        ) }

        return MemberEntity.toDomain(jpaEntity)
    }
}
