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

rootProject.name = "PeTV"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":app")
include(":core")
include(":core:common")
include(":core:designsystem")
include(":core:data")
include(":core:network")
include(":core:database")
include(":core:datastore")
include(":core:domain")
include(":core:model")
include(":core:screenshot-testing")
include(":core:testing")
include(":core:ui")
include(":feature")
include(":feature:home")
