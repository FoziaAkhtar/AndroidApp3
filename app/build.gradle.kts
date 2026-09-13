plugins {
    id("com.android.application")
}

android {
    namespace = "com.foziaakhtar.androidapp3"

    compileSdk = 35

    defaultConfig {
        applicationId = "com.foziaakhtar.androidapp3"

        // Android 8.0 (API 26) is required because
        // this project uses adaptive launcher icons.
        minSdk = 26

        targetSdk = 35

        versionCode = 1

        versionName = "1.0"

        testInstrumentationRunner =
            "androidx.test.runner.AndroidJUnitRunner"
    }


    // =====================================================
    // BUILD TYPES
    // =====================================================

    buildTypes {

        release {

            // We are not using code shrinking for this
            // assignment.
            isMinifyEnabled = false

            proguardFiles(
                getDefaultProguardFile(
                    "proguard-android-optimize.txt"
                ),
                "proguard-rules.pro"
            )
        }
    }


    // =====================================================
    // JAVA COMPATIBILITY
    // =====================================================

    // Java compatibility used by the Android project.
    compileOptions {

        sourceCompatibility =
            JavaVersion.VERSION_11

        targetCompatibility =
            JavaVersion.VERSION_11
    }
}


// =========================================================
// DEPENDENCIES
// =========================================================

dependencies {


    // =====================================================
    // ANDROID CORE
    // =====================================================

    // Provides basic Android functionality and Kotlin
    // extensions used by the application.

    implementation(
        "androidx.core:core-ktx:1.15.0"
    )


    // =====================================================
    // APP COMPAT
    // =====================================================

    // Provides AppCompatActivity and compatibility
    // features for different Android versions.

    implementation(
        "androidx.appcompat:appcompat:1.7.0"
    )


    // =====================================================
    // MATERIAL DESIGN
    // =====================================================

    // Provides Material Design components for the UI.

    implementation(
        "com.google.android.material:material:1.12.0"
    )


    // =====================================================
    // GOOGLE LOCATION SERVICES
    // =====================================================

    // Provides FusedLocationProviderClient.
    // This allows the app to access device location.

    implementation(
        "com.google.android.gms:play-services-location:21.3.0"
    )


    // =====================================================
    // OPENSTREETMAP / OSMANDROID
    // =====================================================

    // Provides OpenStreetMap support for Assignment 6.
    //
    // MapActivity uses:
    // - MapView
    // - GeoPoint
    // - Marker
    // - TileSourceFactory
    // - OpenStreetMap tiles

    implementation(
        "org.osmdroid:osmdroid-android:6.1.20"
    )


    // =====================================================
    // TESTING
    // =====================================================

    // Used for local unit tests.

    testImplementation(
        "junit:junit:4.13.2"
    )


    // Used for Android instrumented tests.

    androidTestImplementation(
        "androidx.test.ext:junit:1.2.1"
    )

    androidTestImplementation(
        "androidx.test.espresso:espresso-core:3.6.1"
    )
}


