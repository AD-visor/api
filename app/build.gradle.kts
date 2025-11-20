apply(plugin = "org.springframework.boot")

dependencies {
    implementation(project(":common"))

    implementation(project(":iam:adapter:inbound"))
    implementation(project(":iam:adapter:outbound"))
    implementation(project(":iam:port:outbound"))

    implementation(project(":conversation:presentation"))
    implementation(project(":subscription:presentation"))
    implementation(project(":payment:presentation"))

    implementation(project(":conversation:infrastructure"))
    implementation(project(":subscription:infrastructure"))
    implementation(project(":payment:infrastructure"))


    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    // Database
    runtimeOnly("org.postgresql:postgresql")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.5")
}

tasks.getByName("bootJar") {
    enabled = true
}

tasks.getByName("jar") {
    enabled = false
}
