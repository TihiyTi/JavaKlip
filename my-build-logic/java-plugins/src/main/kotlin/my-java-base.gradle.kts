plugins {
    id("java")
    id("idea")
    id("org.gradlex.java-module-dependencies")
}
group = "org.example"

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

tasks.test {
    useJUnitPlatform()
    testLogging.showStandardStreams = true
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.3")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

javaModuleDependencies {
    // The mappings here are already known by the plugin. It also works without defining them here.
    moduleNameToGA.put("jakarta.activation", "jakarta.activation:jakarta.activation-api")
    moduleNameToGA.put("org.slf4j", "org.slf4j:slf4j-api")
    moduleNameToGA.put("jakarta.el", "jakarta.el:jakarta.el-api")
    moduleNameToGA.put("jakarta.servlet", "jakarta.servlet:jakarta.servlet-api")
}

//dependencies.constraints {
//    implementation("org.apache.commons:commons-lang3:3.6!!") // Remove !! (strict version) and this will be upgraded
//    implementation("org.apache.commons:commons-text:1.5")
//}
//
//val applicationRuntimeClasspath = configurations.create("applicationRuntimeClasspath") {
//    isCanBeResolved = true
//    isCanBeConsumed = false
//    attributes {
//        attribute(Usage.USAGE_ATTRIBUTE, objects.named(Usage.JAVA_RUNTIME))
//    }
//}
//dependencies {
//    applicationRuntimeClasspath("org.example.my-app:app")
//}
//configurations {
//    compileClasspath.get().shouldResolveConsistentlyWith(applicationRuntimeClasspath)
//    runtimeClasspath.get().shouldResolveConsistentlyWith(applicationRuntimeClasspath)
//    testCompileClasspath.get().shouldResolveConsistentlyWith(applicationRuntimeClasspath)
//    testRuntimeClasspath.get().shouldResolveConsistentlyWith(applicationRuntimeClasspath)
//}