package com.advisor.api.media.port.inbound

import com.advisor.api.media.port.inbound.command.DeleteMediaCommand

interface DeleteMediaUseCase {
    fun execute(command: DeleteMediaCommand)
}
