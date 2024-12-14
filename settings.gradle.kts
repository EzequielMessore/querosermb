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
    ":core:data:data",
    ":core:data:local",
    ":core:data:remote",
    ":core:domain",
    ":core:navigation",
    ":core:presentation:designsystem",
    ":core:view-model",
    ":core:testing",
)

include(
    ":features:details",
    ":features:exchanges",
)
