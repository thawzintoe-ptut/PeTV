val ktlintVersion = "1.0.1"

initscript {
    val spotlessVersion = "6.23.3"

    repositories {
        mavenCentral()
    }

    dependencies {
        classpath("com.diffplug.spotless:spotless-plugin-gradle:$spotlessVersion")
    }
}

rootProject {
    subprojects {
        apply<com.diffplug.gradle.spotless.SpotlessPlugin>()
        extensions.configure<com.diffplug.gradle.spotless.SpotlessExtension> {
            kotlin {
                target("**/*.kt")
                targetExclude("**/build/**/*.kt")
                ktlint(ktlintVersion).editorConfigOverride(
                    mapOf(
                        "android" to "true",
                        "ktlint_code_style" to "android",
                        "ij_kotlin_allow_trailing_comma" to true,
                        "disabled_rules" to
                                    "filename," +
                                    "annotation,annotation-spacing," +
                                    "argument-list-wrapping," +
                                    "double-colon-spacing," +
                                    "enum-entry-name-case," +
                                    "multiline-if-else," +
                                    "no-empty-first-line-in-method-block," +
                                    "package-name," +
                                    "trailing-comma," +
                                    "spacing-around-angle-brackets," +
                                    "spacing-between-declarations-with-annotations," +
                                    "spacing-between-declarations-with-comments," +
                                    "unary-op-spacing"
                    ),
                )
            }
            format("kts") {
                target("**/*.kts")
                targetExclude("**/build/**/*.kts")
            }
        }
    }
}
