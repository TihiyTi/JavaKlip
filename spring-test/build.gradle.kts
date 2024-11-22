plugins {
    id("java")
    id("idea")
    id("org.javamodularity.moduleplugin") version "1.8.15"
    id("org.gradlex.java-module-dependencies") version "1.4.1"

    id("org.springframework.boot") version "3.4.0"
    id("io.spring.dependency-management") version "1.1.6"
}

group = "com.ti"

//java {
//    modularity.inferModulePath.set(true) // Включает модульную систему
//}

dependencies{

    // Spring Boot Starter
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter")

    // Spring Core компоненты
    implementation("org.springframework:spring-core")
    implementation("org.springframework:spring-beans")
    implementation("org.springframework:spring-context")

    // Jakarta Inject API
    implementation("jakarta.inject:jakarta.inject-api:2.0.1")

}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

