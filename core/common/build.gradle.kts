plugins {
    alias(libs.plugins.petv.android.library)
    alias(libs.plugins.petv.android.library.jacoco)
    alias(libs.plugins.petv.android.hilt)
}

android {
    namespace = "${libs.versions.nameSpace.get()}.core.common"
    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
    }
}

dependencies {
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.turbine)
    implementation(libs.arrow.core)
}
