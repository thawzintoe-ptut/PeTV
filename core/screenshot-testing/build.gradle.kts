plugins {
    alias(libs.plugins.petv.android.library)
    alias(libs.plugins.petv.android.library.compose)
    alias(libs.plugins.petv.android.hilt)
}

android {
    namespace = "com.google.samples.apps.nowinandroid.core.screenshottesting"
}

dependencies {
    api(libs.roborazzi)
    implementation(libs.androidx.compose.ui.test)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.ui.test)
    implementation(libs.robolectric)
    implementation(projects.core.common)
    implementation(projects.core.designsystem)
}
