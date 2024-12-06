pluginManagement {
    repositories.gradlePluginPortal()
}

dependencyResolutionManagement {
    repositories.mavenCentral()

    includeBuild("../ti-java-lib")
    includeBuild(".")
}

rootProject.name = "fx-test"

include("fx-one")
include("fx-two")
include("fx-three")
include("fx-four-signal")