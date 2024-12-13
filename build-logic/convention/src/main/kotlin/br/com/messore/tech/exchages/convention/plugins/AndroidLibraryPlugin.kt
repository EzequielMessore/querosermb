package br.com.messore.tech.exchages.convention.plugins

import br.com.messore.tech.exchages.convention.configs.configureKotlinAndroid
import br.com.messore.tech.exchages.convention.extentions.bundle
import br.com.messore.tech.exchages.convention.extentions.libs
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.project

class AndroidLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) = target.run {
        pluginManager.run {
            apply("com.android.library")
            apply("org.jetbrains.kotlin.android")
        }

        extensions.configure<LibraryExtension> {
            namespace = "br.com.messore.tech.exchages${project.path.replace(":", ".").replace("-", "_")}"
            configureKotlinAndroid(this)

            defaultConfig {
                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                consumerProguardFiles("consumer-rules.pro")
            }
        }

        dependencies {
            "testImplementation"(kotlin("test"))
            "testImplementation"(project(":core:testing"))
            "testImplementation"(libs.bundle("testing"))
        }
    }
}