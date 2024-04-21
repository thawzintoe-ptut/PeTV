plugins {
    alias(libs.plugins.petv.android.library)
    alias(libs.plugins.petv.android.library.compose)
    alias(libs.plugins.petv.android.hilt)
}

android {
    namespace = "com.google.samples.apps.nowinandroid.core.testing"
}

dependencies {
    api(kotlin("test"))
    api(libs.androidx.compose.ui.test)
    api(projects.core.data)
    api(projects.core.model)

    debugApi(libs.androidx.compose.ui.testManifest)

    implementation(libs.androidx.test.rules)
    implementation(libs.hilt.android.testing)
    implementation(libs.kotlinx.coroutines.test)
    implementation(libs.kotlinx.datetime)
    implementation(projects.core.common)
    implementation(projects.core.designsystem)
}
