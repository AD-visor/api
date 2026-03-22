plugins {
    kotlin("kapt")
}

dependencies {
    implementation(project(":iam:domain"))
    implementation(project(":iam:port"))
    implementation(project(":common"))

    // Spring
    implementation("org.springframework:spring-context")
    implementation("org.springframework.boot:spring-boot")
    kapt("org.springframework.boot:spring-boot-configuration-processor")
    compileOnly("org.springframework.boot:spring-boot-configuration-processor")

    // JWT
    implementation("io.jsonwebtoken:jjwt-api:0.13.0")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.13.0")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.13.0")

    // Database
    compileOnly("jakarta.persistence:jakarta.persistence-api")
    compileOnly("org.springframework.data:spring-data-jpa")
}
