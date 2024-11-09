plugins{
    id("application") // Gradle Core plugin
    id("checkstyle")
//    id("org.jetbrains.kotlin.jvm") version "1.5.21" // Community plugin
//    id("my_java_library") // Locally defined plugin
}

val myBuildGroup = "my project build"
tasks.named<TaskReportTask>("tasks"){
    displayGroup = myBuildGroup
}
tasks.build {
    group = myBuildGroup
}
tasks.check {
    group = myBuildGroup
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(22))
}

tasks.test{
    useJUnitPlatform()
}

dependencies{
    implementation(project(":business-logic"))
    implementation(project(":data-model"))
    implementation("org.apache.commons:commons-lang3:3.9")
}

application {
    mainClass.set("MyApplication")
}