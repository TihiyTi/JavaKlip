rootProject.name = "GradleTest"

pluginManagement{
    repositories{
        gradlePluginPortal()
        google()
    }
//    includeBuild("../my-build-logic")
}

dependencyResolutionManagement{
    repositories{
        mavenCentral()
    }
//    includeBuild("../my-other-project")
}

include("app")
include("data-model")