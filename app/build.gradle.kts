plugins {
    alias(libs.plugins.app.android.application)
}

android {
    namespace = "br.com.messore.tech.exchages"
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    implementation(projects.core.data)
    implementation(projects.core.domain)
    implementation(projects.core.presentation.designsystem)
}