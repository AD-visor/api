package com.advisor.api.iam.port.inbound.member.listener

import com.advisor.api.common.core.domain.vo.DomainEvent

interface RefreshMemberViewListener {
    fun handle(event: DomainEvent)
}