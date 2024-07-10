package org.example.gradle

import org.gradle.api.Project
import org.gradle.api.attributes.AttributeCompatibilityRule
import org.gradle.api.attributes.CompatibilityCheckDetails
import org.gradle.api.tasks.SourceSetContainer

internal object Schema {

  fun Project.applyRole() {
    extensions.findByType(SourceSetContainer::class.java)?.let { sources ->
      sources.all { sourceSet ->
        // consumables, exposed to consumer projects
        configurations.findByName(sourceSet.apiElementsConfigurationName)
          ?.attributes
          ?.attribute(
            Role.ATTRIBUTE,

            DefaultRole(sourceSet.name)
          )

        configurations.findByName(sourceSet.runtimeElementsConfigurationName)
          ?.attributes
          ?.attribute(
            Role.ATTRIBUTE,
            DefaultRole(sourceSet.name)
          )

        // resolvables, used within a project to resolve dependencies
        configurations.findByName(sourceSet.compileClasspathConfigurationName)
          ?.attributes
          ?.attribute(
            Role.ATTRIBUTE,
            DefaultRole(sourceSet.name)
          )

        configurations.findByName(sourceSet.runtimeClasspathConfigurationName)
          ?.attributes
          ?.attribute(
            Role.ATTRIBUTE,
            DefaultRole(sourceSet.name)
          )
      }
    }
  }

  internal fun Project.applySchema(applyWorkaround: Boolean) {
    dependencies.attributesSchema { schema ->
      schema.attribute(Role.ATTRIBUTE) { matchingStrategy ->
        matchingStrategy.compatibilityRules.add(CompatibilityRule::class.java)
      }

      if (applyWorkaround) {
        // We need a String-based attribute, identical to the Role attribute, due to a Gradle bug.
        logger.quiet("Applying workaround. `consumer:test` should succeed")
        schema.attribute(Role.ATTRIBUTE_COMPAT) { matchingStrategy ->
          matchingStrategy.compatibilityRules.add(CompatibilityRuleCompat::class.java)
        }
      } else {
        logger.quiet("Not applying workaround. `consumer:test` should fail")
      }
    }
  }
}

internal abstract class CompatibilityRule : AttributeCompatibilityRule<Role> {

  override fun execute(details: CompatibilityCheckDetails<Role>) {
    details.compatible()
  }
}

internal abstract class CompatibilityRuleCompat : AttributeCompatibilityRule<String> {

  override fun execute(details: CompatibilityCheckDetails<String>) {
    details.compatible()
  }
}
