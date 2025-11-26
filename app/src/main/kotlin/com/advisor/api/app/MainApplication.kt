package com.advisor.api.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.advisor.api"])
@ConfigurationPropertiesScan
class MainApplication

fun main(args: Array<String>) {
	runApplication<MainApplication>(*args)
}
