pluginManagement {
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

rootProject.name = "JetpackCompose"
include(":Core:Theme")
include(":Core:Logger")
include(":Core:SmoothAnimationBottomBar")
include(":Core:Common")

include(":Apps:TaskManager")
include(":Apps:JCCleanArchitecture")
include(":Apps:LoLData")
include(":Apps:YoutubeAPI")
include(":Apps:SpotifyApi")
include(":Apps:SpotifyApi:Network")
include(":Apps:MediaConverter")
include(":Apps:CircularIndicatorApp")
