plugins {
    id("my-java-application")

    id("org.javamodularity.moduleplugin") version "1.8.15"
//    alias(libs.plugins.javamodularity.moduleplugin)
//    alias(libs.plugins.javafxplugin)
//    alias(libs.plugins.springframework.boot)
//    alias(libs.plugins.spring.dependency.management)

}


myApp {
    mainClass.set("myproject.MyApplication")
}

dependencies{
//    implementation("org.gradlex:java-module-dependencies:1.4.2")

    implementation("org.example:business-logic")
//    implementation(project(":business-logic"))
}