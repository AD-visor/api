plugins {
    id("org.springframework.boot")
    kotlin("kapt")
}

dependencies {
    implementation(project(":common"))

    implementation(project(":iam:adapter:inbound"))
    implementation(project(":iam:adapter:outbound"))
    implementation(project(":iam:port"))
    implementation(project(":iam:application"))

    implementation(project(":subscription:adapter:inbound"))
    implementation(project(":subscription:adapter:outbound"))
    implementation(project(":subscription:port"))
    implementation(project(":subscription:application"))

    implementation(project(":payment:adapter:inbound"))
    implementation(project(":payment:adapter:outbound"))
    implementation(project(":payment:port:outbound"))
    implementation(project(":payment:application"))

    implementation(project(":conversation:adapter:inbound"))
    implementation(project(":conversation:adapter:outbound"))
    implementation(project(":conversation:port:outbound"))
    implementation(project(":conversation:application"))

    implementation(project(":token_usage:adapter:inbound"))
    implementation(project(":token_usage:adapter:outbound"))
    implementation(project(":token_usage:port"))
    implementation(project(":token_usage:application"))

    implementation(project(":media:adapter:inbound"))
    implementation(project(":media:adapter:outbound"))
    implementation(project(":media:port"))
    implementation(project(":media:application"))

    // Spring
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    compileOnly("org.springframework.boot:spring-boot-configuration-processor")
    kapt("org.springframework.boot:spring-boot-configuration-processor")

    // Spring AI
    implementation("org.springframework.ai:spring-ai-starter-model-google-genai")
    implementation("org.springframework.ai:spring-ai-starter-model-openai")

    // OAuth2
    implementation ("org.springframework.boot:spring-boot-starter-oauth2-client")

    // Kotlin Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.10.2")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.10.0")

    // Database
    runtimeOnly("org.postgresql:postgresql")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.5")

    // Test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.springframework.security:spring-security-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.getByName("bootJar") {
    enabled = true
}

tasks.getByName("jar") {
    enabled = false
}
