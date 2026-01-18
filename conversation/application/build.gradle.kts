dependencies {
    implementation(project(":common"))
    implementation(project(":conversation:domain"))
    implementation(project(":conversation:port:outbound"))
    implementation(project(":conversation:port:inbound"))

    implementation(project(":media:port:outbound"))
    implementation(project(":media:port:inbound"))

    implementation(project(":ai_prompt_core"))

    implementation("org.springframework:spring-context")
    implementation("org.springframework:spring-tx")
}
