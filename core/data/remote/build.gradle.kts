plugins {
    alias(libs.plugins.app.jvm.library)
    alias(libs.plugins.app.jvm.ktor)
}

dependencies {
    implementation(projects.core.data.data)
    implementation(projects.core.domain)
}
