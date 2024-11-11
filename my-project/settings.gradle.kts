pluginManagement{
    repositories.gradlePluginPortal()
    includeBuild("../my-build-logic")
}


dependencyResolutionManagement{
    repositories.mavenCentral()
    includeBuild("../my-other-project")
    includeBuild(".")
//    versionCatalogs {
//        create("libs") {
//            from(files("gradle/libs.versions.toml"))
//        }
//    }
}

rootProject.name = "my-project"

include("app")
include("business-logic")
include("data-model")

//enableFeaturePreview("VERSION_CATALOGS")