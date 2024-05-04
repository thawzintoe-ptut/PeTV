plugins {
    alias(libs.plugins.petv.android.library)
    alias(libs.plugins.petv.android.library.compose)
    alias(libs.plugins.petv.android.library.jacoco)
}

android {
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    namespace = "${libs.versions.nameSpace.get()}.core.ui"
}

dependencies {
    api(libs.androidx.metrics)
    api(projects.core.designsystem)
    api(projects.core.model)

    implementation(libs.androidx.browser)
    implementation(libs.coil.kt)
    implementation(libs.coil.kt.compose)

    androidTestImplementation(projects.core.testing)
}
