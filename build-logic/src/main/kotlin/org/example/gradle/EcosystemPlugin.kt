package org.example.gradle

import org.example.gradle.Schema.applySchema
import org.gradle.api.Plugin
import org.gradle.api.Project

abstract class EcosystemPlugin : Plugin<Project> {

  override fun apply(target: Project): Unit = target.run {
    // ./gradlew consumer:test for failure
    // ./gradlew consumer:test -PapplyWorkaround for success
    val shouldApplyWorkaround = providers.gradleProperty("applyWorkaround").isPresent

    applySchema(shouldApplyWorkaround)
  }
}
