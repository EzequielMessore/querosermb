enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Exchages"
include(":app")

include(
    ":data:data",
    ":data:local",
    ":data:remote",
    ":domain",
    ":presentation:designsystem",
)

include(
    ":core:navigation",
    ":core:view-model",
    ":core:testing",
)

include(
    ":features:details",
    ":features:exchanges",
)
