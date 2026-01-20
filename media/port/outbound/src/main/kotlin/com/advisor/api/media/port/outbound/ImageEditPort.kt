package com.advisor.api.media.port.outbound

import com.advisor.api.media.port.outbound.command.ImageEditCommand
import com.advisor.api.media.port.outbound.result.ImageEditResult

interface ImageEditPort {
    fun composite(command: ImageEditCommand.Composite): ImageEditResult
}
