plugins {
    kotlin("kapt")
}

dependencies {
    implementation(project(":media:domain"))
    implementation(project(":media:port:outbound"))
    implementation(project(":common"))

    // Spring
    implementation("org.springframework.boot:spring-boot-starter-web")
    kapt("org.springframework.boot:spring-boot-configuration-processor")
    compileOnly("org.springframework.boot:spring-boot-configuration-processor")

    // Image
    implementation("com.microsoft.playwright:playwright:1.57.0")

    // Database
    compileOnly("jakarta.persistence:jakarta.persistence-api")
    compileOnly("org.springframework.data:spring-data-jpa")
}
