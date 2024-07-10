plugins {
  alias(libs.plugins.jvm)
  id("java-test-fixtures")
  id("maven-publish")

  // build-logic plugins
  id("role")
  id("ecosystem")
}

group = "org.example"
version = "1.0"

publishing {
  publications {
    create<MavenPublication>("maven") {
      from(components["java"])
    }
  }
  repositories {
    maven {
      name = "local"
      url = uri(File(rootDir, "build/repo"))
    }
  }
}

java {
  toolchain {
    languageVersion = JavaLanguageVersion.of(21)
  }
}
