plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.imaisnaini.dailytea"

    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.imaisnaini.dailytea"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner =
            "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "API_URL", "\"https://newsapi.org/v2/\"")
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }

        }
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.core.ktx)

    // Compose BOM
    implementation(platform(libs.androidx.compose.bom))

    // Jetpack Compose
    implementation(libs.compose.material)
    implementation(libs.compose.material.icons.extended)
    implementation(libs.androidx.ui.graphics)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
}