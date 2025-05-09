import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.kotlin.serialization)
}

val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localProperties.load(localPropertiesFile.inputStream())
}

private val youtubeApiKey: String = localProperties.getProperty("youtube_api_key") ?: ""
private val youtubeAppClientId: String = localProperties.getProperty("youtube_app_client_id") ?: ""
private val youtubeAppClientSecret: String = localProperties.getProperty("youtube_app_client_secret") ?: ""

android {
    namespace = "com.apps.youtube.api"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.apps.youtube.api1"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false

            buildConfigField("String", "YOUTUBE_API_KEY", "\"$youtubeApiKey\"")
            buildConfigField("String", "YOUTUBE_APP_CLIENT_ID", "\"$youtubeAppClientId\"")
            buildConfigField("String", "YOUTUBE_APP_CLIENT_SECRET", "\"$youtubeAppClientSecret\"")

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }

        debug {
            buildConfigField("String", "YOUTUBE_API_KEY", "\"$youtubeApiKey\"")
            buildConfigField("String", "YOUTUBE_APP_CLIENT_ID", "\"$youtubeAppClientId\"")
            buildConfigField("String", "YOUTUBE_APP_CLIENT_SECRET", "\"$youtubeAppClientSecret\"")
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
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.appcompat)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.icons.extended)

    implementation(libs.timber)

    implementation(libs.gson)
    implementation(libs.okhttp3)
    implementation(libs.okhttp3.logging.interceptor)
    implementation(libs.retrofit2)
    implementation(libs.retrofit2.converter.gson)

    implementation(libs.kotlinx.serialization.json)

    implementation(libs.androidx.datastore.core)

    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    ksp(libs.hilt.compiler)

    implementation(libs.coil.compose)

    implementation(libs.androidx.core.splash)

    implementation(libs.navigation.compose)

    implementation(libs.lifecycle.viewmodel.compose)
    implementation(libs.lifecycle.runtime.compose)

    implementation(libs.google.auth)

    implementation(libs.mlkit.language.id)
    implementation(libs.mlkit.translate)

    implementation(project(":Core:Theme"))
    implementation(project(":Core:Logger"))
    implementation(project(":Core:Common"))

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}