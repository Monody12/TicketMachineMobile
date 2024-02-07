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

    signingConfigs {
        getByName("debug") {
            storeFile = file("/Users/monody/Documents/Android/test.keystore")
            storePassword = "123456"
            keyPassword = "123456"
            keyAlias = "testalias"
        }
        create("release") {
            storeFile = file("/Users/monody/Documents/Android/test.keystore")
            storePassword = "123456"
            keyAlias = "testalias"
            keyPassword = "123456"
        }
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            signingConfig = signingConfigs.getByName("debug")
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
        kotlinCompilerExtensionVersion = "1.5.8"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}


dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    // lifecycle
    var lifecycle_version = "2.7.0"
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")
    implementation("androidx.compose.runtime:runtime-livedata")


    implementation("androidx.activity:activity-compose:1.8.0")
    implementation(platform("androidx.compose:compose-bom:2024.01.00"))
    implementation("androidx.compose.ui:ui:1.6.0")
    implementation("androidx.compose.ui:ui-graphics:1.6.0")

    implementation("androidx.compose.ui:ui-tooling-preview:1.6.0")
    // navigation
    implementation("androidx.navigation:navigation-compose:2.5.3")
    // material
    implementation("androidx.compose.material:material:1.6.0")
    implementation("androidx.compose.material:material-icons-core:1.6.0")
    implementation("androidx.compose.material:material-icons-extended:1.6.0")
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
    implementation("com.android.volley:volley:1.2.1")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.1")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.google.code.gson:gson:2.9.1")
    // 依赖注入
    implementation("com.google.dagger:hilt-android:2.46.1")
    kapt("com.google.dagger:hilt-android:2.46.1")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
//    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    runtimeOnly("androidx.compose:compose-bom:2024.01.00")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.6.0")
    debugImplementation("androidx.compose.ui:ui-tooling:1.6.0")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.6.0")

    // 兼容低版本api
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.2")

    // 引入百度ocr_ui示例
    implementation(project(":ocr_ui"))
    // 引入百度ocr
    implementation(files("libs/ocrsdk.aar"))
    // 滚动界面
    runtimeOnly("androidx.recyclerview:recyclerview:1.3.2")
}

configurations {
    all {
        exclude(group = "com.google.zxing", module = "core")
    }
}