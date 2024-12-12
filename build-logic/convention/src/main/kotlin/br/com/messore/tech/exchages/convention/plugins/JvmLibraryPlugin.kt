package br.com.messore.tech.exchages.convention.plugins

import br.com.messore.tech.exchages.convention.configs.configureKotlin
import br.com.messore.tech.exchages.convention.extentions.bundle
import br.com.messore.tech.exchages.convention.extentions.library
import br.com.messore.tech.exchages.convention.extentions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.project

class JvmLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("com.google.devtools.ksp")
                apply("org.jetbrains.kotlin.jvm")
            }

            configureKotlin()

            dependencies {
                "implementation"(libs.library("coroutines-core"))

                "implementation"(libs.bundle("koin"))
                "ksp"(libs.library("koin-ksp-compiler"))

                "testImplementation"(kotlin("test"))
                "testImplementation"(project(":core:testing"))
                "testImplementation"(libs.bundle("testing"))
                "testImplementation"(libs.library("testing-koin"))
            }
        }
    }
}
