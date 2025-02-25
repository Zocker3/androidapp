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
        maven("https://developer.dji.com/maven")
        maven {
            setUrl("https://api.mapbox.com/downloads/v2/")
            credentials {
                username = "mapbox"
                password = System.getenv("sk.eyJ1IjoiejMzMTM2Mzk5IiwiYSI6ImNtN2tiN2FnNTBhcWoya3Mzd2lheGF6angifQ.K7SbzVfwomQRJjyUSnIPfw") ?: providers.gradleProperty("sk.eyJ1IjoiejMzMTM2Mzk5IiwiYSI6ImNtN2tiN2FnNTBhcWoya3Mzd2lheGF6angifQ.K7SbzVfwomQRJjyUSnIPfw").getOrElse("")
            }
        }
    }
}

rootProject.name = "My Application"
include(":app")
 