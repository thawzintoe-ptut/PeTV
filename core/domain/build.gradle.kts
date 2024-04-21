plugins {
    alias(libs.plugins.petv.android.library)
    alias(libs.plugins.petv.android.library.jacoco)
    id("com.google.devtools.ksp")
}

android {
    namespace = "${libs.versions.nameSpace.get()}.core.domain"
}

dependencies {
    api(projects.core.data)
    api(projects.core.model)

    implementation(libs.javax.inject)

    testImplementation(projects.core.testing)
}
