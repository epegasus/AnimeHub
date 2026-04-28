plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "com.sohaib.animehub.data"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(project(":core-common"))
    implementation(project(":core-database"))
    implementation(project(":core-network"))
    implementation(project(":domain"))

    // Android Core
    implementation(libs.androidx.core.ktx)

    // Koin
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}