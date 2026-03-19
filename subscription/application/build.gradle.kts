dependencies {
    implementation(project(":common"))
    implementation(project(":subscription:domain"))
    implementation(project(":subscription:port"))

    implementation("org.springframework:spring-context")
    implementation("org.springframework:spring-tx")
}
