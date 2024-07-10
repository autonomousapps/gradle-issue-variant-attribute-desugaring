package org.example.gradle

import org.gradle.api.Named
import org.gradle.api.attributes.Attribute
import java.io.Serializable

interface Role : Named {

  companion object {
    val ATTRIBUTE: Attribute<Role> = Attribute.of("role", Role::class.java)
    val ATTRIBUTE_COMPAT: Attribute<String> = Attribute.of("role", String::class.java)
  }
}

internal class DefaultRole(
  private val sourceSetName: String
) : Role, Serializable {

  override fun getName(): String = sourceSetName
}
