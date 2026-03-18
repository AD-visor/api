dependencies {
    implementation(project(":common"))
    implementation(project(":iam:domain"))
    implementation(project(":iam:port"))

    implementation("org.springframework:spring-context")
    implementation("org.springframework:spring-tx")
}
