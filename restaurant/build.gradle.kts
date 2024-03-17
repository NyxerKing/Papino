plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "ru.papino.restaurant"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        kapt {
            arguments { arg("room.schemaLocation", "$projectDir/schemas") }
        }
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
    val material = "1.11.0"
    val retrofit = "2.9.0"
    val gson = "2.9.0"
    val lifecycleviewmodel = "2.7.0"
    val corektx = "1.12.0"
    val appcompat = "1.6.1"
    val constraintlayout = "2.1.4"
    val recyclerview = "1.3.2"
    val glide = "4.16.0"
    val room_version = "2.6.1"

    implementation("com.squareup.retrofit2:retrofit:$retrofit")
    implementation("com.squareup.retrofit2:converter-gson:$gson")
    implementation("com.google.android.material:material:$material")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleviewmodel")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleviewmodel")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleviewmodel")
    implementation("com.github.bumptech.glide:glide:$glide")
    implementation("androidx.constraintlayout:constraintlayout:$constraintlayout")
    implementation("androidx.recyclerview:recyclerview:$recyclerview")

    implementation("androidx.room:room-runtime:$room_version")
    kapt("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")

    implementation("androidx.core:core-ktx:$corektx")
    implementation("androidx.appcompat:appcompat:$appcompat")

    implementation(project(":uikit"))
}