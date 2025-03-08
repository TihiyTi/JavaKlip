plugins {
    id("java")
    id("idea")
    id("application")
    id("org.beryx.jlink") version "2.26.0"

    id("org.javamodularity.moduleplugin") version "1.8.15"
//    id("org.gradlex.java-module-dependencies") version "1.4.1"

    id("org.openjfx.javafxplugin") version "0.1.0"
}

group = "com.ti.desktop"

idea {
    module {
        inheritOutputDirs = true // Указывает использовать output-путь проекта
    }
}

java {
    modularity.inferModulePath.set(false)
}

application {
  mainModule.set("com.ti.desktop")
  mainClass.set("com.ti.desktop.Remg")
}

dependencies{
    implementation("com.ti:ti-utils")

    implementation("com.fazecast:jSerialComm:2.11.0")

    testImplementation("org.junit.jupiter:junit-jupiter:5.11.0")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.11.0")

    implementation("org.openjfx:javafx-base:21.0.6-ea+2:win")
    implementation("org.openjfx:javafx-graphics:21.0.6-ea+2:win")
    implementation("org.openjfx:javafx-fxml:21.0.6-ea+2:win")
    implementation("org.openjfx:javafx-web:21.0.6-ea+2:win")

    implementation("org.slf4j:slf4j-api:2.0.11")  // SLF4J API
    implementation("ch.qos.logback:logback-classic:1.4.14")  // Logback (реализация SLF4J)

    implementation("commons-io:commons-io:2.15.1")

   // implementation("jakarta.xml.bind:jakarta.xml.bind-api:4.0.0")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}
javafx {
    modules("javafx.controls", "javafx.fxml")
}
tasks.jar {
    manifest {
        attributes["Main-Class"] = "com.ti.desktop.Remg"
    }
}

jlink {
    options.addAll("--strip-debug", "--compress", "2", "--no-header-files", "--no-man-pages", "--bind-services")
    imageName.set("remg")
    launcher {
        name = "remg"
        jvmArgs.add("-Dlogback.configurationFile=../conf/logback.xml")
    }
    addExtraDependencies("javafx")
    addExtraDependencies("slf4j")
    addExtraDependencies("logback")

}

tasks.register<Copy>("copyPropertiesToJlink") {
    from("$projectDir/Remg.properties")  // <-- путь к твоему файлу в проекте (можно src/main/config если хочешь)
    into("$buildDir/remg/bin")     // <-- куда копируем в jlink-образ

}

tasks.jlink {
    finalizedBy("copyPropertiesToJlink")
}