// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.2" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
}
// 引入华为SDK 添加华为maven仓库，url http://developer.huawei.com/repo/
buildscript {
    repositories {
        maven("https://developer.huawei.com/repo/")
    }
}
allprojects {
    repositories {
        maven("https://developer.huawei.com/repo/")
    }
}