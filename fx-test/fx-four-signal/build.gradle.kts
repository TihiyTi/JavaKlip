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
  mainClass.set("com.ti.desktop.FxFourSignal")
}

dependencies{
    implementation("com.ti:ti-utils")

    implementation("com.fazecast:jSerialComm:2.11.0")

    implementation("org.fxmisc.richtext:richtextfx:0.11.0")
    implementation("org.fxmisc.flowless:flowless:0.6.10")

    testImplementation("org.junit.jupiter:junit-jupiter:5.11.0")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.11.0")

    implementation("org.openjfx:javafx-base:21.0.6-ea+2:win")
    implementation("org.openjfx:javafx-graphics:21.0.6-ea+2:win")
    implementation("org.openjfx:javafx-fxml:21.0.6-ea+2:win")
    implementation("org.openjfx:javafx-web:21.0.6-ea+2:win")

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
        attributes["Main-Class"] = "com.ti.desktop.FxFourSignal"
    }
}

jlink {
    options.addAll("--strip-debug", "--compress", "2", "--no-header-files", "--no-man-pages")
    imageName.set("fx-four-signal")
    launcher {
        name = "fx-four-signal"
    }
    addExtraDependencies("javafx")
}