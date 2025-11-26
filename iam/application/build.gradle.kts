dependencies {
    implementation(project(":common"))
    implementation(project(":iam:domain"))
    implementation(project(":iam:port:outbound"))
    implementation(project(":iam:port:inbound"))

    implementation("org.springframework:spring-context")
    implementation("org.springframework:spring-tx")
}
