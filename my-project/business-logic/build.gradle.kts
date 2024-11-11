plugins {
    id("my-java-library")
}

dependencies {
//    implementation("org.gradlex:java-module-dependencies:1.4.2")

    implementation(libs.commons.lang3)
//    implementation(libs.slf4j.api)
    implementation("org.example:shared-utils")

    api("org.example:data-model")

//    api("org.example.my-app:data-model") //alternative: project(":data-model")

//    compileOnlyApi("com.google.errorprone:error_prone_annotations")
}