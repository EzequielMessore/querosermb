plugins {
    alias(libs.plugins.app.jvm.library)
    alias(libs.plugins.app.jvm.ktor)
}

dependencies {
    implementation(projects.data.data)
    implementation(projects.domain)
}
