package br.com.messore.tech.exchages.convention.model

data class AppVersion(
    val minSdk: Int,
    val compileSdk: Int,
    val versionCode: Int,
    val versionName: String,
    val applicationId: String,
)
