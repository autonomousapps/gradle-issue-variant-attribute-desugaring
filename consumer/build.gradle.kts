plugins {
  alias(libs.plugins.jvm)
  id("java-test-fixtures")

  // build-logic plugin
  id("ecosystem")
}

dependencies {
  testImplementation(testFixtures("org.example:producer:1.0"))
  testImplementation(libs.junit.jupiter)

  testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

java {
  toolchain {
    languageVersion = JavaLanguageVersion.of(21)
  }
}

tasks.named<Test>("test") {
  useJUnitPlatform()
}
