dependencies {
    implementation(project(":common"))
    implementation(project(":conversation:domain"))
    implementation(project(":conversation:port:outbound"))
    implementation(project(":conversation:port:inbound"))

    implementation("org.springframework:spring-context")
    implementation("org.springframework:spring-tx")
}
