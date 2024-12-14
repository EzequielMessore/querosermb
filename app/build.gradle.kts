import java.util.Properties

plugins {
    alias(libs.plugins.app.android.application)
    alias(libs.plugins.ksp)
}

val properties = Properties().apply {
    val file = rootProject.file("local.properties")
    if (file.exists()) load(file.reader())
}

android {
    namespace = "br.com.messore.tech.exchages"
    buildFeatures.buildConfig = true

    defaultConfig {
        buildConfigField("String", "API_URL", "\"https://rest.coinapi.io/v1/\"")
        buildConfigField("String", "API_KEY", properties.property("API_KEY"))
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

private fun Properties.property(name: String, default: String = "\"\""): String {
    val env = System.getenv(name) ?: getProperty(name, default)
    return "\"$env\""
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.compose.navigation)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    implementation(libs.bundles.koin)
    implementation(libs.bundles.koin.android)
    ksp(libs.koin.ksp.compiler)

    implementation(libs.kotlinx.serialization.json)

    implementation(libs.timber)

    implementation(projects.core.data.data)
    implementation(projects.core.data.local)
    implementation(projects.core.data.remote)
    implementation(projects.core.domain)
    implementation(projects.core.presentation.designsystem)
    implementation(projects.core.viewModel)
    implementation(projects.core.navigation)

    implementation(projects.features.exchanges)
    implementation(projects.features.details)
}
