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
            url = uri("https://api.mapbox.com/downloads/v2/releases/maven")
        }
        maven { url= uri ("https://maven.aliyun.com/repository/public") }
        maven { url = uri ("https://maven.aliyun.com/repository/google")}
        maven { url = uri ("https://repo.huaweicloud.com/repository/maven") }
    }
}

rootProject.name = "My Application"
include(":app")
 