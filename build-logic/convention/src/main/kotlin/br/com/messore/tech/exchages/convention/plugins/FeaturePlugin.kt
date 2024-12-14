package br.com.messore.tech.exchages.convention.plugins

import br.com.messore.tech.exchages.convention.configs.composeDependencies
import br.com.messore.tech.exchages.convention.extentions.bundle
import br.com.messore.tech.exchages.convention.extentions.library
import br.com.messore.tech.exchages.convention.extentions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.project

class FeaturePlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("app.compose.library")
                apply("com.google.devtools.ksp")
                apply("org.jetbrains.kotlin.plugin.serialization")
            }

            dependencies {
                "implementation"(project(":domain"))
                "implementation"(project(":core:view-model"))
                "implementation"(project(":core:navigation"))
                "implementation"(project(":presentation:designsystem"))
                "implementation"(libs.library("ktor-serialization-kotlinx-json"))

                "implementation"(libs.bundle("koin"))
                "implementation"(libs.bundle("koin-android"))
                "ksp"(libs.findLibrary("koin-ksp-compiler").get())

                "testImplementation"(project(":core:testing"))

                "androidTestImplementation"(kotlin("test"))
                "androidTestImplementation"(project(":core:testing"))
                "androidTestImplementation"(libs.library("testing-ui-test-junit4"))
            }
            composeDependencies()
        }
    }
}
