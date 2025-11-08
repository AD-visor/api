dependencies {
    implementation(project(":payment:domain"))
    implementation(project(":common"))

    // Spring
    implementation("org.springframework:spring-context")

    // Database
    compileOnly("jakarta.persistence:jakarta.persistence-api")
    compileOnly("org.springframework.data:spring-data-jpa")
    compileOnly("org.mybatis:mybatis:3.5.19")
    compileOnly("org.mybatis:mybatis-spring:3.0.5")
}
