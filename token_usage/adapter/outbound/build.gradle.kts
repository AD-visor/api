plugins {
    kotlin("kapt")
}

dependencies {
    implementation(project(":token_usage:domain"))
    implementation(project(":token_usage:port:outbound"))
    implementation(project(":common"))

    // Spring
    implementation("org.springframework:spring-context")
    implementation("org.springframework.boot:spring-boot")
    kapt("org.springframework.boot:spring-boot-configuration-processor")
    compileOnly("org.springframework.boot:spring-boot-configuration-processor")

    // Database
    compileOnly("jakarta.persistence:jakarta.persistence-api")
    compileOnly("org.springframework.data:spring-data-jpa")
    compileOnly("org.mybatis:mybatis:3.5.19")
    compileOnly("org.mybatis:mybatis-spring:3.0.5")
}
