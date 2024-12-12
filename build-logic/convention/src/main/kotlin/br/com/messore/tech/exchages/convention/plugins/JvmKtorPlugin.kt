package br.com.messore.tech.exchages.convention.plugins

import br.com.messore.tech.exchages.convention.extentions.bundle
import br.com.messore.tech.exchages.convention.extentions.library
import br.com.messore.tech.exchages.convention.extentions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class JvmKtorPlugin: Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.apply("org.jetbrains.kotlin.plugin.serialization")

            dependencies {
                "implementation"(libs.bundle("ktor"))
                "implementation"(libs.library("kotlinx-serialization-json"))

                "testImplementation"(libs.library("testing-ktor"))
            }
        }
    }
}
