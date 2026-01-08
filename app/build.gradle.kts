plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    // Add the Google services Gradle plugin
    //id("com.google.gms.google-services")
    alias(libs.plugins.google.services)
    alias(libs.plugins.google.firebase.crashlytics)
}

android {
    signingConfigs {
        create("Firebase Upload") {
        }
    }
    namespace = "com.pessilogroup.drivesight"
    //compileSdk = 36
    compileSdk {
        version = release(36)
    }
    defaultConfig {
        applicationId = "com.pessilogroup.drivesight"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        externalNativeBuild {
            cmake {
                cppFlags += "-std=c++17"
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
            version = "3.22.1"
        }
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    // Import the Firebase BoM
    //implementation(platform("com.google.firebase:firebase-bom:34.7.0"))
    // TODO: Add the dependencies for Firebase products you want to use
    // When using the BoM, don't specify versions in Firebase dependencies
    // https://firebase.google.com/docs/android/setup#available-libraries
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    // TODO: Add the dependencies for Firebase products you want to use
    // When using the BoM, don't specify versions in Firebase dependencies
    implementation(libs.google.firebase.analytics)
    implementation(libs.firebase.crashlytics)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}