plugins {
    id("my-java-application")

    id("org.javamodularity.moduleplugin") version "1.8.15"
//    alias(libs.plugins.javamodularity.moduleplugin)
//    alias(libs.plugins.javafxplugin)
    alias(libs.plugins.springframework.boot)
    alias(libs.plugins.spring.dependency.management)

}


myApp {
    mainClass.set("myproject.MyApplication")
}

dependencies{
//    implementation("org.gradlex:java-module-dependencies:1.4.2")

//    implementation("org.springframework:spring-framework-bom:6.1.14")
    implementation(platform(libs.spring.bom))
    implementation(libs.bundles.spring.boot)
    implementation(libs.inject.api)

    implementation("org.example:business-logic")
//    implementation(project(":business-logic"))
}