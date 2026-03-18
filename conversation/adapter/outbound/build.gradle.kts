plugins {
    kotlin("kapt")
}

dependencies {
    implementation(project(":conversation:domain"))
    implementation(project(":conversation:port:outbound"))
    implementation(project(":common"))

    implementation(project(":ai_prompt_core"))

    implementation(project(":media:port"))

    // Spring
    implementation("org.springframework:spring-context")
    implementation("org.springframework.boot:spring-boot")
    kapt("org.springframework.boot:spring-boot-configuration-processor")
    compileOnly("org.springframework.boot:spring-boot-configuration-processor")

    // Spring AI
    implementation("org.springframework.ai:spring-ai-starter-model-google-genai")
    implementation("org.springframework.ai:spring-ai-starter-model-openai")

    // Database
    compileOnly("jakarta.persistence:jakarta.persistence-api")
    compileOnly("org.springframework.data:spring-data-jpa")
    compileOnly("org.mybatis:mybatis:3.5.19")
    compileOnly("org.mybatis:mybatis-spring:3.0.5")

    // Kotlin Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.10.2")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.10.0")
}
