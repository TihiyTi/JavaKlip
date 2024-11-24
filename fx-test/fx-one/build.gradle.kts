plugins {
    id("java")
    id("idea")
    id("application")

}

group = "com.ti"

application {

  mainClass.set("com.ti.desktop.FxOne")
}

dependencies{}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}
