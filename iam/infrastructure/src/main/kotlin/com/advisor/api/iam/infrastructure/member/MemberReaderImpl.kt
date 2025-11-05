package com.advisor.api.iam.infrastructure.member

import com.advisor.api.common.exception.CustomException
import com.advisor.api.iam.domain.member.MemberReader
import com.advisor.api.iam.domain.member.MemberView
import org.springframework.stereotype.Repository

@Repository
class MemberReaderImpl(
    private val memberViewMapper: MemberViewMapper
): MemberReader {
    override fun findById(id: Long): MemberView{
        val memberEntity = memberViewMapper.findById(id).orElseThrow{ CustomException(
            MemberInfrastructureExceptionCode.MEMBER_NOT_FOUND,
            "[Member] ${id}에 해당하는 회원이 존재하지 않습니다."
        ) }

        return MemberViewEntity.toModel(memberEntity)
    }
}
