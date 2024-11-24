plugins {
    id("java")
    id("idea")
    id("application")

    id("org.javamodularity.moduleplugin") version "1.8.15"
    id("org.gradlex.java-module-dependencies") version "1.4.1"

    id("org.openjfx.javafxplugin") version "0.1.0"
}

group = "com.ti.desktop"

application {
  mainModule.set("com.ti.desktop")
  mainClass.set("com.ti.desktop.FxThree")
}

dependencies{
    implementation("org.openjfx:javafx-base:'21.0.6-ea+2'")
    implementation("org.openjfx:javafx-graphics:'21.0.6-ea+2'")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}
javafx {
    modules("javafx.controls", "javafx.fxml")
}
