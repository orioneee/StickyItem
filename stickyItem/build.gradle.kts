import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.jetbrains.compose.internal.utils.localPropertiesFile

plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.compose)
    alias(libs.plugins.compose.compiler)
    id("maven-publish")
    id("com.vanniktech.maven.publish") version "0.31.0"
}

group = "org.oriooneee"
version = "1.0.0"

kotlin {
    jvmToolchain(17)

    androidTarget { publishLibraryVariants("release") }
    jvm()
//    js { browser() }
//    wasmJs { browser() }
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
        }

    }

    //https://kotlinlang.org/docs/native-objc-interop.html#export-of-kdoc-comments-to-generated-objective-c-headers
    targets.withType<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget> {
        compilations["main"].compileTaskProvider.configure {
            compilerOptions {
                freeCompilerArgs.add("-Xexport-kdoc")
            }
        }
    }

}

android {
    namespace = "org.oriooneee"
    compileSdk = 35

    defaultConfig {
        minSdk = 21
    }
}

publishing {
    repositories {
        maven {
            name = "githubPackages"
            url = uri("https://maven.pkg.github.com/orioneee/StickyItem")
            val githubPackagesUsername = gradleLocalProperties(rootDir, providers).getProperty("githubUsername")
            val githubToken = gradleLocalProperties(rootDir, providers).getProperty("githubToken")
//            val githubEmailAddress = gradleLocalProperties(rootDir, providers).getProperty("githubEmailAddress")
            credentials{
                username = githubPackagesUsername
                password = githubToken
            }

        }
    }
}

mavenPublishing {
    coordinates(
        groupId = "org.oriooneee",
        artifactId = "sticky-item",
        version = "1.0.0"
    )

    pom {
        name.set("Sticky item")
//        description.set("Sample Kotlin MultiPlatform Library for Purple Shared")
        inceptionYear.set("2025")
        url.set("https://github.com/orioneee/StickyItem")

        licenses {
            license {
                name.set("MIT")
                url.set("https://opensource.org/licenses/MIT")
            }
        }
        developers {
            developer {
                id.set(gradleLocalProperties(rootDir, providers).getProperty("githubUsername"))
                name.set(gradleLocalProperties(rootDir, providers).getProperty("githubUsername"))
                email.set(gradleLocalProperties(rootDir, providers).getProperty("githubEmailAddress"))
            }
        }

        scm {
            url.set("https://github.com/orioneee/StickyItem")
        }
    }
}