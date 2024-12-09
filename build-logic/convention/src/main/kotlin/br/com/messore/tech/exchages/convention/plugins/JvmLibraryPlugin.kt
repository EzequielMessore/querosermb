package br.com.messore.tech.exchages.convention.plugins

import br.com.messore.tech.exchages.convention.configs.configureKotlin
import org.gradle.api.Plugin
import org.gradle.api.Project

class JvmLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            pluginManager.apply("org.jetbrains.kotlin.jvm")

            configureKotlin()
        }
    }
}
