apply(plugin = "org.springframework.boot")

dependencies {
    implementation(project(":common"))
    implementation(project(":iam:presentation"))
    implementation(project(":content:presentation"))
    implementation(project(":subscription:presentation"))
    implementation(project(":payment:presentation"))
}

tasks.getByName("bootJar") {
    enabled = true
}

tasks.getByName("jar") {
    enabled = false
}
