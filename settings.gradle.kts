pluginManagement {
  includeBuild("build-logic")

  repositories {
    gradlePluginPortal()
  }
}

plugins {
  id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
    maven(url = File(rootDir, "build/repo"))
    maven(url = "https://maven.global.square/artifactory/square-public")
  }
}

rootProject.name = "reproducer"

include(":consumer")
include(":producer")
