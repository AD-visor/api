dependencies {
    implementation(project(":conversation:domain"))
    implementation(project(":common"))

    // Spring
    implementation("org.springframework:spring-context")

    // Database
    compileOnly("jakarta.persistence:jakarta.persistence-api")
    compileOnly("org.springframework.data:spring-data-jpa")
}
