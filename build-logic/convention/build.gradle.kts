plugins {
    `kotlin-dsl`
}

group = "${libs.versions.nameSpace}.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    compileOnly(libs.android.tools.build.gradle.plugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.firebase.crashlytics.gradlePlugin)
    compileOnly(libs.firebase.performance.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.room.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("petvAndroidApplication") {
            id = "petv.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("petvAndroidLibrary") {
            id = "petv.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("petvAndroidTest") {
            id = "petv.android.test"
            implementationClass = "AndroidTestConventionPlugin"
        }
        register("petvCompose") {
            id = "petv.compose"
            implementationClass = "ComposeConventionPlugin"
        }
        register("petvDynamic") {
            id = "petv.dynamic"
            implementationClass = "DynamicFeatureConventionPlugin"
        }
        register("petvFlavors") {
            id = "petv.android.application.flavors"
            implementationClass = "AndroidApplicationFlavorsConventionPlugin"
        }
        register("androidApplicationCompose") {
            id = "petv.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "petv.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidHilt") {
            id = "petv.android.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }
    }
}