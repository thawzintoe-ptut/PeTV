plugins {
    alias(libs.plugins.petv.jvm.library)
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    api(libs.kotlinx.serialization.json)
    api(libs.kotlinx.datetime)
    api(libs.arrow.core)
    api(libs.arrow.fx)
}
