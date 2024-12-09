plugins {
    `kotlin-dsl`
}

group = "br.com.messore.tech.exchages.build.logic"

dependencies {
    compileOnly(gradleApi())
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "app.android.application"
            implementationClass = "br.com.messore.tech.exchages.convention.plugins.AndroidApplicationPlugin"
        }
        register("androidLibrary") {
            id = "app.android.library"
            implementationClass = "br.com.messore.tech.exchages.convention.plugins.AndroidLibraryPlugin"
        }
        register("composeLibrary") {
            id = "app.compose.library"
            implementationClass = "br.com.messore.tech.exchages.convention.plugins.ComposePlugin"
        }
        register("jvmLibrary") {
            id = "app.jvm.library"
            implementationClass = "br.com.messore.tech.exchages.convention.plugins.JvmLibraryPlugin"
        }
        register("feature") {
            id = "app.feature"
            implementationClass = "br.com.messore.tech.exchages.convention.plugins.FeaturePlugin"
        }
        register("jvmKtor") {
            id = "app.jvm.ktor"
            implementationClass = "br.com.messore.tech.exchages.convention.plugins.JvmKtorPlugin"
        }
    }
}
