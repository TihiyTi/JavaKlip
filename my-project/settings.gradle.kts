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
//    includeBuild("../my-build-logic")
}

include("app")
include("data-model")
include("business-logic")
