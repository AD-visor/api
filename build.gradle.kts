plugins {
	kotlin("jvm") version "2.2.21"
	kotlin("plugin.spring") version "2.2.21"
	id("org.springframework.boot") version "4.0.0" apply false
	id("io.spring.dependency-management") version "1.1.7"
	kotlin("plugin.jpa") version "2.2.21" apply false
	kotlin("kapt") version "2.2.21" apply false
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

	dependencyManagement {
		imports {
			mavenBom("org.springframework.boot:spring-boot-dependencies:4.0.0")
			mavenBom("org.springframework.ai:spring-ai-bom:1.1.2")
		}
	}

	dependencies {
		// Kotlin
		implementation("tools.jackson.module:jackson-module-kotlin")
		implementation("org.jetbrains.kotlin:kotlin-reflect")
		implementation("io.github.microutils:kotlin-logging:3.0.5")
	}

	kotlin {
		compilerOptions {
			jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
			freeCompilerArgs.add("-Xjsr305=strict")
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
