plugins {
    alias(libs.plugins.app.android.library)
    alias(libs.plugins.app.jvm.ktor)
    alias(libs.plugins.ksp)
}

android {
    namespace = "br.com.messore.tech.exchange.core.data"
}

dependencies {
    implementation(libs.bundles.koin)
    implementation(libs.koin.annotations)
    ksp(libs.koin.ksp.compiler)

    implementation(projects.core.domain)
}