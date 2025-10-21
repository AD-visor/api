dependencies {
    implementation(project(":subscription:domain"))
    implementation(project(":common"))

    runtimeOnly("org.postgresql:postgresql")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
}
