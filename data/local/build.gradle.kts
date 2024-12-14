plugins {
    alias(libs.plugins.ksp)
    alias(libs.plugins.app.android.library)
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    implementation(projects.data.data)
    implementation(projects.domain)
    implementation(libs.androidx.datastore.preferences)

    ksp(libs.koin.ksp.compiler)
    implementation(libs.bundles.koin)
    implementation(libs.kotlinx.serialization.json)
}