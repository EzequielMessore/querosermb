package br.com.messore.tech.exchages.convention.configs

import br.com.messore.tech.exchages.convention.extentions.Extension
import br.com.messore.tech.exchages.convention.extentions.getAppVersion
import br.com.messore.tech.exchages.convention.extentions.libs
import br.com.messore.tech.exchages.convention.model.AppJavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

internal fun Project.configureKotlinAndroid(extension: Extension) {
    val appVersion = libs.getAppVersion()
    extension.apply {
        compileSdk = appVersion.compileSdk

        compileOptions {
            sourceCompatibility = AppJavaVersion.JAVA_VERSION
            targetCompatibility = AppJavaVersion.JAVA_VERSION
        }
    }

    configureKotlin()
}

private fun Project.configureKotlin() {
    tasks.withType<KotlinJvmCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(AppJavaVersion.JVM_TARGET)
            freeCompilerArgs.add("-opt-in=kotlin.RequiresOptIn")
        }
    }
}
