import org.gradle.internal.impldep.org.junit.experimental.categories.Categories.CategoryFilter.exclude
import org.jetbrains.kotlin.ir.backend.js.compile

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.kapt")
}

android {
    namespace = "com.example.ticketmachinemobile"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.ticketmachinemobile"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
        // 兼容低版本api
        isCoreLibraryDesugaringEnabled = true
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    kotlin {
        jvmToolchain(17)
    }
    buildFeatures {
        compose = true
        dataBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}


dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.0")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    // navigation
    implementation("androidx.navigation:navigation-compose:2.5.3")
    // material
    implementation("androidx.compose.material:material")
    implementation("androidx.compose.material:material-icons-core")
    implementation("androidx.compose.material:material-icons-extended")
    // huawei Scan Kit
    implementation("com.huawei.hms:scanplus:2.12.0.301")
    implementation(files("libs/OTG.jar"))
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    // 扫码
    // 相机
    val cameraxVersion = "1.2.0-alpha04"
    implementation("androidx.camera:camera-camera2:$cameraxVersion")
    implementation("androidx.camera:camera-lifecycle:$cameraxVersion")
    implementation("androidx.camera:camera-view:$cameraxVersion")

    // mlkit
//    implementation("com.google.mlkit:barcode-scanning:17.0.2")
//    implementation("com.google.mlkit:text-recognition:16.0.0-beta4")
//    implementation("com.google.mlkit:text-recognition-chinese:16.0.0-beta4")
    // 工具栏
    implementation("androidx.appcompat:appcompat:1.5.1")
    implementation("com.google.android.material:material:1.4.0")
    // activity-ktx
    implementation("androidx.activity:activity-ktx:1.8.1")


    // 申请权限
    val accompanistVersion = "0.23.1"
    implementation("com.google.accompanist:accompanist-permissions:$accompanistVersion")
    // zxing
    implementation("com.google.zxing:core:3.4.1")
    // 手持机身份证读卡器依赖
    api("org.greenrobot:eventbus:3.2.0")
    api(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    api(files("libs/qspdasdk.jar"))
    // 网络
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
//    compileOnly("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.google.code.gson:gson:2.9.1")
    // 依赖注入
    implementation("com.google.dagger:hilt-android:2.46.1")
    kapt("com.google.dagger:hilt-android:2.46.1")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // 兼容低版本api
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.2")
}

configurations {
    all {
        exclude(group = "com.google.zxing", module = "core")
    }
}