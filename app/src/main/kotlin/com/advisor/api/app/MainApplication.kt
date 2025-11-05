package com.advisor.api.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.advisor.api"])
class MainApplication

fun main(args: Array<String>) {
	runApplication<MainApplication>(*args)
}
