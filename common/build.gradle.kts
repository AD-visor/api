dependencies {
    // Spring
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework:spring-context")

    // Database
    compileOnly("jakarta.persistence:jakarta.persistence-api")
    compileOnly("org.springframework.boot:spring-boot-starter-data-jpa")

    // Kafka
    implementation("org.springframework.kafka:spring-kafka")
}
