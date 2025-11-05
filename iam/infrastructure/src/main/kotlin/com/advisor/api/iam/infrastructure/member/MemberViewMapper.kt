package com.advisor.api.iam.infrastructure.member

import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select
import java.util.Optional

@Mapper
interface MemberViewMapper {
    @Select("SELECT * FROM vw_member WHERE id = #{id}")
    fun findById(
        @Param("id") id: Long
    ): Optional<MemberViewEntity>
}
