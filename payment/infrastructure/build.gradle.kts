dependencies {
    implementation(project(":payment:domain"))
    implementation(project(":common"))

    // Spring
    implementation("org.springframework:spring-context")

    // Database
    runtimeOnly("org.postgresql:postgresql")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
}
