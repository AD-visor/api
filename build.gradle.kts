plugins {
	kotlin("jvm") version "2.2.21"
	kotlin("plugin.spring") version "2.2.21"
	id("org.springframework.boot") version "4.0.0" apply false
	id("io.spring.dependency-management") version "1.1.7" apply false
	kotlin("plugin.jpa") version "2.2.21" apply false
}

group = "com.advisor"
version = "0.0.1-SNAPSHOT"
description = "advisor api server"

allprojects {
	repositories {
		mavenCentral()
	}
}

subprojects {
	apply(plugin = "org.jetbrains.kotlin.jvm")
	apply(plugin = "org.jetbrains.kotlin.plugin.spring")
	apply(plugin = "io.spring.dependency-management")
	apply(plugin = "org.jetbrains.kotlin.plugin.jpa")

	java {
		toolchain {
			languageVersion = JavaLanguageVersion.of(21)
		}
	}

	configurations {
		compileOnly {
			extendsFrom(configurations.annotationProcessor.get())
		}
	}

	dependencies {
		// Spring
		implementation(platform("org.springframework.boot:spring-boot-dependencies:4.0.0"))
		annotationProcessor(platform(("org.springframework.boot:spring-boot-dependencies:4.0.0")))

		// Kotlin
		implementation("tools.jackson.module:jackson-module-kotlin")
		implementation("org.jetbrains.kotlin:kotlin-reflect")
		implementation("io.github.microutils:kotlin-logging:3.0.5")

		// Spring AI
		implementation(platform("org.springframework.ai:spring-ai-bom:1.1.2"))

		// Test
		testImplementation("org.springframework.boot:spring-boot-starter-test")
		testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
		testImplementation("org.springframework.security:spring-security-test")
		testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	}

	kotlin {
		compilerOptions {
			freeCompilerArgs.addAll("-Xjsr305=strict")
		}
	}

	allOpen {
		annotation("jakarta.persistence.Entity")
		annotation("jakarta.persistence.MappedSuperclass")
		annotation("jakarta.persistence.Embeddable")
	}

	tasks.withType<Test> {
		useJUnitPlatform()
	}

	tasks.withType<Jar> {
		val modulePath = project.path
			.removePrefix(":")
			.replace(":", "-")

		archiveBaseName.set(modulePath)
	}
}
