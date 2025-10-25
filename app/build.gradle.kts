apply(plugin = "org.springframework.boot")

dependencies {
    implementation(project(":common"))
    implementation(project(":iam:presentation"))
    implementation(project(":content:presentation"))
    implementation(project(":subscription:presentation"))
    implementation(project(":payment:presentation"))

    implementation(project(":iam:infrastructure"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
}

tasks.getByName("bootJar") {
    enabled = true
}

tasks.getByName("jar") {
    enabled = false
}
