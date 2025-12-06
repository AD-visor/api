dependencies {
    implementation(project(":subscription:port:inbound"))
    implementation(project(":common"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.security:spring-security-web")
}
