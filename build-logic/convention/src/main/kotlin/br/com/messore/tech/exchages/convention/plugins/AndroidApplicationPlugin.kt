package br.com.messore.tech.exchages.convention.plugins

import br.com.messore.tech.exchages.convention.configs.configureKotlinAndroid
import br.com.messore.tech.exchages.convention.extentions.bundle
import br.com.messore.tech.exchages.convention.extentions.getAppVersion
import br.com.messore.tech.exchages.convention.extentions.library
import br.com.messore.tech.exchages.convention.extentions.libs
import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) = target.run {
        pluginManager.run {
            apply("com.android.application")
            apply("org.jetbrains.kotlin.android")
            apply("org.jetbrains.kotlin.plugin.compose")
        }
        extensions.configure<ApplicationExtension> {
            configureKotlinAndroid(this)

            defaultConfig {
                val appVersion = libs.getAppVersion()

                applicationId = appVersion.applicationId
                minSdk = appVersion.minSdk

                targetSdk = appVersion.compileSdk
                versionCode = appVersion.versionCode
                versionName = appVersion.versionName
            }

            buildFeatures {
                compose = true
            }
        }
        composeDependencies()
    }
}

private fun Project.composeDependencies() = dependencies {
    val compose = libs.bundle("compose")
    val composeDebug = libs.bundle("compose-debug")

    val bom = libs.library("androidx-compose-bom")

    add("implementation", platform(bom))
    add("implementation", compose)
    add("debugImplementation", composeDebug)
}
