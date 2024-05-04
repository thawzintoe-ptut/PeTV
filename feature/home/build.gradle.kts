plugins {
    alias(libs.plugins.petv.android.feature)
    alias(libs.plugins.petv.android.library.compose)
    alias(libs.plugins.petv.android.library.jacoco)
    alias(libs.plugins.roborazzi)
}

android {
    namespace = "${libs.versions.nameSpace.get()}.feature.foryou"
}

dependencies {
    implementation(libs.accompanist.permissions)
    implementation(projects.core.data)
    implementation(projects.core.domain)

    testImplementation(libs.hilt.android.testing)
    testImplementation(libs.robolectric)
    testImplementation(projects.core.testing)
    testImplementation(projects.core.screenshotTesting)

    androidTestImplementation(projects.core.testing)
}
