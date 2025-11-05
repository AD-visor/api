package com.advisor.api.iam.infrastructure.auth

import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select
import java.util.Optional

@Mapper
interface AuthViewMapper {
    @Select(
        "SELECT * FROM vw_auth WHERE member_id = #{member_id}",
    )
    fun findByMemberId(@Param("member_id") memberId: Long): Optional<AuthViewEntity>
}
