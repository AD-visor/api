package com.advisor.api.common.core.infrastructure

import org.springframework.stereotype.Component

@Component
class SnowFlakeIdUtil(
    private val snowFlakeProperties: SnowFlakeProperties
) {
    init {
        val datacenterId = snowFlakeProperties.datacenterId
        val workerId = snowFlakeProperties.workerId
    }

    private val epoch = 1680000000000L
    private val datacenterIdBits = 5
    private val workerIdBits = 5
    private val sequenceBits = 12

    // private const val MAX_DATACENTER_ID = (1L shl DATACENTER_ID_BITS) - 1
    // private const val MAX_WORKER_ID = (1L shl WORKER_ID_BITS) - 1
    private val maxSequence = (1L shl sequenceBits) - 1

    private var lastTimestamp = -1L
    private var sequence = 0L

    @Synchronized
    fun generateId(): Long {
        var currentTimestamp = currentTime()

        if (currentTimestamp < lastTimestamp) {
            throw IllegalStateException("현재 시간이 마지막 timestamp 보다 이전에 있습니다")
        }

        if (currentTimestamp == lastTimestamp) {
            sequence = (sequence + 1) and maxSequence
            if (sequence == 0L) currentTimestamp = waitNextMillis(currentTimestamp)
        } else {
            sequence = 0
        }

        lastTimestamp = currentTimestamp

        return ((currentTimestamp - epoch) shl (datacenterIdBits + workerIdBits + sequenceBits)) or
                (snowFlakeProperties.datacenterId shl (workerIdBits + sequenceBits)) or
                (snowFlakeProperties.workerId shl sequenceBits) or
                sequence
    }

    private fun waitNextMillis(currentTimestamp: Long): Long {
        var ts = currentTime()
        while (ts <= currentTimestamp) {
            ts = currentTime()
        }
        return ts
    }

    private fun currentTime(): Long = System.currentTimeMillis()
}
