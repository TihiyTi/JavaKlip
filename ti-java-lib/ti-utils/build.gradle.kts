plugins {
    id("java")
//    id("idea")
//    id("java-library")
//    id("org.gradlex.java-module-dependencies")
//    id("org.javamodularity.moduleplugin") version "1.8.15"
//    id("org.gradlex.java-module-dependencies") version "1.4.1"

}

java {
    modularity.inferModulePath.set(true)
}

group = "com.ti"

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.11.0")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.11.0")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform() // Обеспечивает запуск тестов через JUnit 5
}