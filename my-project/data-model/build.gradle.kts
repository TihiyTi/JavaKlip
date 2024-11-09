plugins{
    id("java-library") // Gradle Core plugin
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(22))
}

tasks.test{
    useJUnitPlatform()
}

dependencies{
    implementation("org.apache.commons:commons-lang3:3.9")
}