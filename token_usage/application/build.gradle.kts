dependencies {
    implementation(project(":common"))
    implementation(project(":token_usage:domain"))
    implementation(project(":token_usage:port"))

    implementation("org.springframework:spring-context")
    implementation("org.springframework:spring-tx")
}
