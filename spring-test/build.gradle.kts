plugins {
    id("java")
    id("idea")
    id("application")
    id("org.javamodularity.moduleplugin") version "1.8.15"
    id("org.gradlex.java-module-dependencies") version "1.4.1"

    alias(libs.plugins.javafxplugin)

    id("org.springframework.boot") version "3.4.0"
    id("io.spring.dependency-management") version "1.1.6"
}

group = "com.ti"

application {
    mainModule.set("com.ti")
    mainClass.set("com.ti.FxApplication")
}
//java {
//    modularity.inferModulePath.set(true) // Включает модульную систему
//}

tasks.named<JavaExec>("run") {
    classpath = sourceSets["main"].runtimeClasspath
}

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

    implementation(libs.javafx.base)
    //implementation(libs.javafx.fxml)
    //implementation(libs.javafx.controls)
    implementation(libs.javafx.graphics)

}



javafx {
    modules("javafx.controls", "javafx.fxml")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}


