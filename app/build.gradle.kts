apply(plugin = "org.springframework.boot")

dependencies {
    implementation(project(":common"))
    implementation(project(":iam"))
    implementation(project(":content"))
    implementation(project(":subscription"))
    implementation(project(":payment"))
}

tasks.getByName("bootJar") {
    enabled = true
}

tasks.getByName("jar") {
    enabled = false
}
