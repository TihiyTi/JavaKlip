plugins {
    id("java")
    id("idea")
    id("application")

    id("org.javamodularity.moduleplugin") version "1.8.15"
    id("org.gradlex.java-module-dependencies") version "1.4.1"
}

group = "com.ti.desktop"

application {
  mainModule.set("com.ti.desktop")
  mainClass.set("com.ti.desktop.FxTwo")
}

dependencies{}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}
