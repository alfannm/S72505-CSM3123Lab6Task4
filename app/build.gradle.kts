// Plugin configuration for this Android application module
plugins {
    alias(libs.plugins.android.application)   // Android application plugin
    alias(libs.plugins.kotlin.android)         // Kotlin support for Android
}

// Android-specific build configuration
android {

    // Application namespace (used for R class and package structure)
    namespace = "com.example.tabsensorlab"

    // SDK version used to compile the app
    compileSdk {
        version = release(36)
    }

    // Default configuration applied to all build variants
    defaultConfig {
        applicationId = "com.example.tabsensorlab"   // Unique app identifier
        minSdk = 24                                  // Minimum Android version supported
        targetSdk = 36                               // Target Android version
        versionCode = 1                              // Internal version number
        versionName = "1.0"                          // User-visible version name

        // Instrumentation runner for UI testing
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    // Build type configuration (debug/release)
    buildTypes {
        release {
            isMinifyEnabled = false                  // Disable code shrinking for release
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    // Java compatibility settings for the project
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    // Kotlin compiler configuration
    kotlinOptions {
        jvmTarget = "11"
    }
}

// Project dependencies required by the application
dependencies {

    // Core Android and Kotlin extensions
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)

    // Material Design components
    implementation(libs.material)
    implementation("com.google.android.material:material:1.11.0")

    // Activity and layout support libraries
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // Unit testing dependencies
    testImplementation(libs.junit)

    // Android instrumentation testing dependencies
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
