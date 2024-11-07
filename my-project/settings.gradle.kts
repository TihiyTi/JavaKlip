rootProject.name = "my-project"

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
    includeBuild("../my-lib-project")
}

include("app")
include("data-model")