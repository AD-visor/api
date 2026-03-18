dependencies {
    implementation(project(":common"))
    implementation(project(":conversation:domain"))
    implementation(project(":conversation:port"))

    implementation(project(":media:port"))

    implementation(project(":ai_prompt_core"))

    // Spring
    implementation("org.springframework:spring-context")
    implementation("org.springframework:spring-tx")

    // Kotlin Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.10.2")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.10.0")
}
