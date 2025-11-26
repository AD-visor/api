dependencies {
    implementation(project(":common"))
    implementation(project(":subscription:domain"))
    implementation(project(":subscription:port:outbound"))
    implementation(project(":subscription:port:inbound"))

    implementation("org.springframework:spring-context")
    implementation("org.springframework:spring-tx")
}
