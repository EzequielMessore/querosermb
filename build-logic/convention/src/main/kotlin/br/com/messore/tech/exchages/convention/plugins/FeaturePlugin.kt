package br.com.messore.tech.exchages.convention.plugins

import br.com.messore.tech.exchages.convention.configs.composeDependencies
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project

class FeaturePlugin: Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.apply("app.compose.library")

            dependencies {
                "implementation"(project(":core:presentation:designsystem"))
            }
            composeDependencies()
        }
    }
}