plugins {
    alias(libs.plugins.petv.android.library)
    alias(libs.plugins.petv.android.library.jacoco)
    alias(libs.plugins.petv.android.hilt)
    alias(libs.plugins.petv.android.room)
}

android {
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    namespace = "${libs.versions.nameSpace.get()}.core.database"
}

dependencies {
    api(projects.core.model)

    implementation(libs.kotlinx.datetime)
    androidTestImplementation(projects.core.testing)
    // room
    implementation(libs.room.runtime)
    ksp(libs.room.compiler)
    implementation(libs.room.ktx)
    implementation(libs.room.paging)
    // gson
    implementation(libs.gson)
}
