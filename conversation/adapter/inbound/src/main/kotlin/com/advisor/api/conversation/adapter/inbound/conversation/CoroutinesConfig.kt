package com.advisor.api.conversation.adapter.inbound.conversation

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import mu.KotlinLogging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CoroutinesConfig {

    private val logger = KotlinLogging.logger {}

    @Bean
    fun applicationScope(): CoroutineScope {
        val exceptionHandler = CoroutineExceptionHandler { context, exception ->
            logger.error("코루틴 에러 발생: $context", exception)
        }

        return CoroutineScope(
            SupervisorJob() +
                    Dispatchers.Default +
                    CoroutineName("ApplicationScope") +
                    exceptionHandler
        )
    }
}
