// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.detekt)
}

dependencies {
    detektPlugins(libs.detekt.formatting)
}

detekt {
    toolVersion = libs.versions.detekt.get()
    val defaultSources = getSrcDirectors()
    val files = properties.getOrDefault("source", defaultSources)?.let {
        if (it is String) it.split(",") else it
    }

    source.setFrom(project.files(files))
    config.setFrom(files("$projectDir/detekt/detekt.yml"))
    reportsDir = file("$projectDir/build/reports/detekt/")
}

/**
 * function responsible for retrieving all `src` folders of the project
 * on this moment we are ignoring the legacy folder in the future this folder will be removed
 */
fun getSrcDirectors() = projectDir.walk().filter { file ->
    file.isDirectory && file.absolutePath.endsWith("src")
}.toList()

tasks {
    val installGitHooks by registering(Copy::class) {
        from(File(rootProject.rootDir, "scripts/pre-commit"))
        into(File(rootProject.rootDir, ".git/hooks"))
        fileMode = 0b000_111_101_101 // 0755 in binary, it doesn't seem to work if I put 755 or 0755
    }

    // Install hooks automatically before building a new compilation
    // Idea from: https://gist.github.com/KenVanHoeylandt/c7a928426bce83ffab400ab1fd99054a
    getByPath(":app:preBuild").dependsOn(installGitHooks)
}