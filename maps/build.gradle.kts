plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "ru.papino.maps"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    viewBinding.enable = true
}

dependencies {

    val corektx = "1.12.0"
    val appcompat = "1.6.1"
    val yandex_map = "4.5.2-navikit"
    val constraintlayout = "2.1.4"

    implementation("androidx.core:core-ktx:$corektx")
    implementation("androidx.appcompat:appcompat:$appcompat")
    implementation("androidx.constraintlayout:constraintlayout:$constraintlayout")

    implementation("com.yandex.android:maps.mobile:$yandex_map")

    implementation(project(":uikit"))
}