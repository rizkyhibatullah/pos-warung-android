plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.example.pos_warung"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.pos_warung"
        minSdk = 30
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

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
    buildFeatures {
        compose = true
    }

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    // Menambahkan ViewModel dengan Compose
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    // Menambahkan hilt
    // Library utama Hilt
    implementation(libs.hilt.android)
    // Integrasi Hilt dengan Navigation Compose
    implementation(libs.androidx.hilt.navigation.compose)
    // Annotation processor Hilt (menggunakan KSP)
    ksp(libs.hilt.compiler)
    // Library utama Room
    implementation(libs.androidx.room.runtime)
    // Dukungan coroutine dan Flow untuk Room
    implementation(libs.androidx.room.ktx)
    // Annotation processor Room (menggunakan KSP)
    ksp(libs.androidx.room.compiler)
    // Library HTTP Client untuk melakukan request API
    implementation(libs.retrofit)
    // Converter untuk mengubah JSON menjadi objek Kotlin (menggunakan Gson)
    //noinspection GradleDependency
    implementation(libs.converter.gson)
    // Interceptor untuk logging request dan response API (sangat membantu saat debugging)
    //noinspection NewerVersionAvailable
    implementation(libs.logging.interceptor)
    // Core library coroutine, digunakan di semua layer (termasuk domain)
    implementation(libs.kotlinx.coroutines.core)
    // Dukungan coroutine untuk Android (main thread, dll)
    implementation(libs.kotlinx.coroutines.android)
    // Library untuk memuat dan menampilkan gambar dari URL atau sumber lain
    implementation(libs.coil.compose)
    // Framework untuk unit testing
    testImplementation(libs.junit)
    // Library untuk membuat objek 'palsu' (mock) dalam testing
    testImplementation(libs.mockito.mockito.core)
    testImplementation(libs.mockito.mockito.inline) // Untuk mock final class
    // Utilitas untuk testing coroutine
    //noinspection NewerVersionAvailable
    testImplementation(libs.kotlinx.coroutines.test)
    // Untuk testing Room dengan in-memory database
    testImplementation(libs.androidx.room.testing)
    // Framework untuk instrumented testing (testing di perangkat/emulator)
    androidTestImplementation(libs.androidx.junit)
    //noinspection GradleDependency
    androidTestImplementation(libs.androidx.espresso.core)
    // Debug tool untuk Compose
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)


}