plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.firebase.appdistribution")
}

android {
    namespace = "com.example.righttriangle"
    compileSdk = 30

    defaultConfig {
        applicationId = "com.example.righttriangle"
        minSdk = 24
        targetSdk = 30
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        viewBinding = true
    }
}

kotlin {
    jvmToolchain(17)
}

firebaseAppDistributionDefault {
    appId = System.getenv("FIREBASE_APP_ID") ?: ""
    testers = System.getenv("FIREBASE_TESTERS") ?: ""
    releaseNotes = "Construction Calculator tester build"
    serviceCredentialsFile = System.getenv("GOOGLE_APPLICATION_CREDENTIALS")
}
