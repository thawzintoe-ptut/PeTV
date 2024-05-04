plugins {
    alias(libs.plugins.petv.android.library)
    alias(libs.plugins.petv.android.library.jacoco)
    alias(libs.plugins.petv.android.hilt)
    alias(libs.plugins.petv.android.room)
    id("kotlinx-serialization")
}

android {
    namespace = "${libs.versions.nameSpace.get()}.core.data"
}

dependencies {
    api(projects.core.common)
    api(projects.core.database)
    api(projects.core.datastore)
    api(projects.core.network)

    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.kotlinx.serialization.json)
    testImplementation(projects.core.testing)
    // paging
    implementation(libs.androidx.paging.compose)
}
