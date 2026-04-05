dependencies {
    implementation(project(":token_usage:port"))
    implementation(project(":common"))

    // Spring
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.security:spring-security-web")

    // Kafka
    implementation("org.springframework.kafka:spring-kafka")
}
