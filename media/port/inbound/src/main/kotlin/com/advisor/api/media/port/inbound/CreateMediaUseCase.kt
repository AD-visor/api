package com.advisor.api.media.port.inbound

import com.advisor.api.media.port.inbound.command.CreateMediaCommand

interface CreateMediaUseCase {
    fun execute(commands: List<CreateMediaCommand>)
}
