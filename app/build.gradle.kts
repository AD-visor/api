apply(plugin = "org.springframework.boot")

dependencies {
    implementation(project(":common"))

    implementation(project(":iam:adapter:inbound"))
    implementation(project(":iam:adapter:outbound"))
    implementation(project(":iam:port:outbound"))
    implementation(project(":iam:application"))

    implementation(project(":subscription:adapter:inbound"))
    implementation(project(":subscription:adapter:outbound"))
    implementation(project(":subscription:port:outbound"))
    implementation(project(":subscription:application"))

    implementation(project(":payment:adapter:inbound"))
    implementation(project(":payment:adapter:outbound"))
    implementation(project(":payment:port:outbound"))
    implementation(project(":payment:application"))

    implementation(project(":conversation:adapter:inbound"))
    implementation(project(":conversation:adapter:outbound"))
    implementation(project(":conversation:port:outbound"))
    implementation(project(":conversation:application"))

    // Spring
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    // OAuth2
    implementation ("org.springframework.boot:spring-boot-starter-oauth2-client")

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
