dependencies {
    implementation(project(":conversation:domain"))
    implementation(project(":conversation:port:outbound"))
    implementation(project(":common"))

    // Spring
    implementation("org.springframework:spring-context")
    implementation("org.springframework.boot:spring-boot")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    // Spring AI
    implementation("org.springframework.ai:spring-ai-starter-model-google-genai")

    // Database
    compileOnly("jakarta.persistence:jakarta.persistence-api")
    compileOnly("org.springframework.data:spring-data-jpa")
    compileOnly("org.mybatis:mybatis:3.5.19")
    compileOnly("org.mybatis:mybatis-spring:3.0.5")
}
