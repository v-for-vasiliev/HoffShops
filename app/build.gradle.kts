plugins {
    id("com.android.application")
    id("version-catalog")
    kotlin("android")
    kotlin("plugin.serialization") version "1.6.21"
    kotlin("kapt")
}

android {
    compileSdk = 32

    defaultConfig {
        applicationId = "ru.vasiliev.hoffshops"
        minSdk = 28
        targetSdk = 32
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    packagingOptions {
        resources.excludes.addAll(
            listOf(
                "META-INF/DEPENDENCIES"
            )
        )
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.8.0")
    implementation("androidx.appcompat:appcompat:1.4.2")
    implementation("com.google.android.material:material:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.fragment:fragment-ktx:1.5.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")

    // Dagger2
    implementation(libs.dagger2.dagger)
    kapt(libs.dagger2.kapt)

    // RxJava
    implementation(libs.rxjava.rxandroid)
    implementation(libs.rxjava.rxjava)

    // Serialization
    implementation(libs.kotlin.serialization)

    // Network
    implementation(libs.okhttp.okhttp)
    implementation(libs.okhttp.interceptor)
    implementation(libs.ktor.core)
    implementation(libs.ktor.android)
    implementation(libs.ktor.content.negotiation)
    implementation(libs.ktor.serialization)

    // Moxy
    implementation(libs.moxy.moxy)
    implementation(libs.moxy.androidx)
    implementation(libs.moxy.ktx)
    implementation(libs.moxy.compiler)
}