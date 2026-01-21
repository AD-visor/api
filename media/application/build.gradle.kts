dependencies {
    implementation(project(":common"))
    implementation(project(":media:domain"))
    implementation(project(":media:port:outbound"))
    implementation(project(":media:port:inbound"))

    implementation("org.springframework:spring-context")
    implementation("org.springframework:spring-tx")
}
