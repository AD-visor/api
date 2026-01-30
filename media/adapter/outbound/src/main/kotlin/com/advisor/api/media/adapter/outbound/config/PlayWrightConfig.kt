package com.advisor.api.media.adapter.outbound.config

import com.microsoft.playwright.Browser
import com.microsoft.playwright.Playwright
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class PlayWrightConfig {
    @Bean(destroyMethod = "close")
    fun playwright(): Playwright {
        return Playwright.create()
    }

    @Bean(destroyMethod = "close")
    fun playwrightBrowser(playwright: Playwright): Browser {
        return playwright.chromium().launch()
    }
}
