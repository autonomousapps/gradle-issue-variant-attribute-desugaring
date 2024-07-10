package org.example.gradle

import org.example.gradle.Schema.applyRole
import org.gradle.api.Plugin
import org.gradle.api.Project

abstract class RolePlugin : Plugin<Project> {

  override fun apply(target: Project): Unit = target.run {
    applyRole()
  }
}
