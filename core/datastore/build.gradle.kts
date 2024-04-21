plugins {
    alias(libs.plugins.petv.android.library)
    alias(libs.plugins.petv.android.library.jacoco)
    alias(libs.plugins.petv.android.hilt)
}

android {
    defaultConfig {
        consumerProguardFiles("consumer-proguard-rules.pro")
    }
    namespace = "${libs.versions.nameSpace.get()}.core.datastore"
    testOptions {
        unitTests {
            isReturnDefaultValues = true
        }
    }
}

dependencies {
    api(libs.androidx.dataStore.core)
    api(projects.core.model)

    implementation(projects.core.common)

    testImplementation(libs.kotlinx.coroutines.test)
}
