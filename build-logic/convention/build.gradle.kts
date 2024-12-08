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
        register("kotlinLibrary") {
            id = "app.kotlin.library"
            implementationClass = "br.com.messore.tech.exchages.convention.plugins.KotlinLibraryPlugin"
        }
    }
}
