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
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode = RepositoriesMode.FAIL_ON_PROJECT_REPOS
    repositories {
        google()
        mavenCentral()
        maven("https://api.xposed.info/")
        maven("https://jitpack.io/")
        maven("https://developer.huawei.com/repo")
        maven("https://developer.hihonor.com/repo")
        maven("https://mirrors.tencent.com/nexus/repository/maven-public/")
        maven("https://oss.sonatype.org/content/repositories/snapshots/")
    }
}

rootProject.name = "WifiCountryCode"
include(":app")
