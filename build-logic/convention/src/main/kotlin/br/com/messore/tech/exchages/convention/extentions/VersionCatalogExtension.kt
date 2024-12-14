package br.com.messore.tech.exchages.convention.extentions

import br.com.messore.tech.exchages.convention.model.AppVersion
import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

typealias Extension = CommonExtension<*, *, *, *, *, *>

val Project.libs: VersionCatalog
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

fun VersionCatalog.versionToInt(alias: String) = version(alias).toInt()

fun VersionCatalog.version(alias: String): String = findVersion(alias).get().requiredVersion

fun VersionCatalog.library(alias: String) = findLibrary(alias).get()

fun VersionCatalog.bundle(alias: String) = findBundle(alias).get()

fun VersionCatalog.getAppVersion(): AppVersion {
    val minSdk = versionToInt("minSdk")
    val versionCode = versionToInt("versionCode")
    val versionName = version("versionName")
    val applicationId = version("applicationId")
    val compileSdk = versionToInt("compileSdkVersion")

    return AppVersion(
        minSdk = minSdk,
        compileSdk = compileSdk,
        versionCode = versionCode,
        versionName = versionName,
        applicationId = applicationId,
    )
}
