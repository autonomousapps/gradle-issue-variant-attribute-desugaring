plugins {
  alias(libs.plugins.jvm)
  id("java-gradle-plugin")
}

gradlePlugin {
  plugins {
    create("ecosystem") {
      id = "ecosystem"
      implementationClass = "org.example.gradle.EcosystemPlugin"
    }
    create("role") {
      id = "role"
      implementationClass = "org.example.gradle.RolePlugin"
    }
  }
}
