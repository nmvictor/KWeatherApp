
plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    kotlin("plugin.serialization")
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = true
        }
    }
    
    sourceSets {
        val ktorVersion = "2.3.10"
        val kodeinVersion = "7.21.2"
        commonMain.dependencies {
            // put your Multiplatform dependencies here
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.cio)
            implementation(libs.koin.core)
            implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
            implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
            api("dev.icerock.moko:mvvm-core:0.13.1")
            // KODE IN
            implementation ("org.kodein.di:kodein-di:$kodeinVersion")

            implementation("io.ktor:ktor-client-logging:$ktorVersion")
        }
    }
}

android {
    namespace = "org.letshego.kweather.app.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}
