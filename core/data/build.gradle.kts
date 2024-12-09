import java.util.Properties

plugins {
    alias(libs.plugins.app.android.library)
    alias(libs.plugins.app.jvm.ktor)
    alias(libs.plugins.ksp)
}

val properties = Properties().apply {
    val file = rootProject.file("local.properties")
    if (file.exists()) load(file.reader())
}

android {
    namespace = "br.com.messore.tech.exchange.core.data"
    buildFeatures.buildConfig = true

    defaultConfig {
        buildConfigField("String", "BASE_URL", "\"https://rest.coinapi.io/v1/\"")
        buildConfigField("String", "API_KEY", properties.property("API_KEY"))
    }
}

private fun Properties.property(name: String, default: String = "\"\""): String {
    val env = System.getenv(name) ?: getProperty(name, default)
    return "\"$env\""
}

dependencies {
    implementation(libs.bundles.koin)
    implementation(libs.koin.annotations)
    ksp(libs.koin.ksp.compiler)

    implementation(libs.timber)
    implementation(projects.core.domain)
}