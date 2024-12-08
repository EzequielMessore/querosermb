package br.com.messore.tech.exchages.convention.plugins

import br.com.messore.tech.exchages.convention.model.AppJavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.configure

class KotlinLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) = target.run {
        pluginManager.run {
            apply("java-library")
            apply("org.jetbrains.kotlin.jvm")
        }

        extensions.configure<JavaPluginExtension> {
            sourceCompatibility = AppJavaVersion.JAVA_VERSION
            targetCompatibility = AppJavaVersion.JAVA_VERSION
        }
    }
}
