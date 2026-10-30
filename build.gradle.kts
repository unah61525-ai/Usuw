plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}
android {
    namespace = "com.agung.xsettings"
    compileSdk = 35
    defaultConfig {
        applicationId = "com.agung.xsettings"
        minSdk = 23
        targetSdk = 35
        versionCode = 1413
        versionName = "14.1.3"
    }
}
dependencies {
    implementation("androidx.core:core-ktx:1.15.0")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("dev.rikka.shizuku:api:13.1.5")
    implementation("dev.rikka.shizuku:provider:13.1.5")
}
