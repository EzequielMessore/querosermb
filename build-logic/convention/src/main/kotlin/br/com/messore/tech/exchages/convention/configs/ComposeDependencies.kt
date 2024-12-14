package br.com.messore.tech.exchages.convention.configs

import br.com.messore.tech.exchages.convention.extentions.bundle
import br.com.messore.tech.exchages.convention.extentions.library
import br.com.messore.tech.exchages.convention.extentions.libs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

fun Project.composeDependencies() = dependencies {
    val compose = libs.bundle("compose")
    val bom = libs.library("androidx-compose-bom")
    val composeDebug = libs.bundle("compose-debug")

    "implementation"(platform(bom))
    "implementation"(compose)
    "debugImplementation"(composeDebug)
}
