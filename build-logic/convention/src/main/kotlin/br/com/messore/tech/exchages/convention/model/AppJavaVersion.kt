package br.com.messore.tech.exchages.convention.model

import org.gradle.api.JavaVersion
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

object AppJavaVersion {
    internal val JAVA_VERSION = JavaVersion.VERSION_17
    internal val JVM_TARGET = JvmTarget.JVM_17
}
