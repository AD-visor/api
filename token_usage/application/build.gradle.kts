dependencies {
    implementation(project(":common"))
    implementation(project(":token_usage:domain"))
    implementation(project(":token_usage:port:outbound"))
    implementation(project(":token_usage:port:inbound"))

    implementation("org.springframework:spring-context")
    implementation("org.springframework:spring-tx")
}
