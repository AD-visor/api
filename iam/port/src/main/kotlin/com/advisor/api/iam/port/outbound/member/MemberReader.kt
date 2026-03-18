package com.advisor.api.iam.port.outbound.member

import com.advisor.api.iam.port.inbound.member.view.MemberView

interface MemberReader {
    fun findById(id: Long): MemberView
    fun refreshView()
}
