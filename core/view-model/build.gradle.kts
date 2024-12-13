plugins {
    alias(libs.plugins.app.android.library)
}

dependencies {
    implementation(libs.coroutines.core)
    implementation(libs.androidx.lifecycle.viewModelCompose)
}
