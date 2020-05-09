@file:Suppress("ClassName", "SpellCheckingInspection", "unused")

package com.iziwork.buildsrc

object Versions {
    // Gradle
    const val androidGradlePlugin = "3.6.3"
    const val fabricGradlePlugin = "1.31.2"
    const val ktlintGradlePlugin = "9.2.1"
    const val googleServices = "4.3.3"

    // Build
    const val androidCompileSdk = 29
    const val androidBuildTools = "29.0.3"
    const val androidMinSdk = 21
    const val androidTargetSdk = 29

    const val kotlin = "1.3.61"
    const val kotlinHtml = "0.7.1"

    object androidX {
        const val activity = "1.1.0"
        const val annotations = "1.1.0"
        const val appcompat = "1.2.0-alpha02"
        const val camera = "1.0.0-beta02"
        const val cameraView = "1.0.0-alpha09"
        const val cardView = "1.0.0"
        const val collection = "1.1.0"
        const val constraintLayout = "1.1.3"
        const val coordinatorlayout = "1.1.0"
        const val core = "1.2.0-rc01"
        const val fragment = "1.2.0"
        const val legacySupport = "1.0.0"
        const val lifecycle = "2.2.0"
        const val navigation = "2.2.1"
        const val palette = "1.0.0"
        const val recycler = "1.1.0"
        const val room = "2.2.3"
        const val swipeRefreshLayout = "1.1.0-alpha03"
        const val vectorDrawable = "1.1.0"
        const val viewPager = "1.0.0"
        const val workManager = "2.3.0"

        object test {
            // https://developer.android.com/jetpack/androidx/releases/test#1.2.0
            const val core = "1.2.0"
            const val espresso = "3.2.0"
            const val runner = "1.2.0"
            const val truth = "1.2.0"
            const val junit = "1.1.1"
            const val monitor = "1.2.0"
            const val rules = "1.2.0"
            const val orchestrator = "1.2.0"
            const val barista = "3.2.0"
        }
    }

    const val materialComponents = "1.2.0-alpha06"

    const val dagger = "2.26"
    const val assistedInject = "0.5.2"

    object rx {
        const val java = "2.2.17"
        const val android = "2.1.1"
        const val kotlin = "2.4.0"
    }

    // Network
    const val retrofit = "2.6.2"
    const val okHttp = "3.14.4"

    const val moshi = "1.9.2"

    object parse {
        const val core = "1.22.1"
        const val facebook = "83d7edb"
    }

    // Log
    const val timber = "4.7.1"

    const val applanga = "3.0.123"

    const val facebook = "5.15.1"

    // UI Libs
    const val glide = "4.11.0"
    const val shimmer = "0.5.0"
    const val materialDialogs = "2.0.2"
    const val spinnerDatePicker = "4265db38f9"
    const val photoView = "2.3.0"
    const val markdown = "4.2.0"
    const val androidSvg = "1.4"
    const val recyclerAnimators = "3.0.0"
    const val shapeOfView = "1.4.7"
    const val lottie = "3.2.2"

    // Fabric/Firebase
    const val crashlytics = "2.10.1"
    const val firebase = "17.2.1"
    const val firebaseCM = "20.0.1"

    // Play service
    const val playCore = "1.6.4"

    object playServices {
        const val places = "2.0.0"

        object auth {
            const val apiPhone = "16.0.0"
        }
    }

    const val installReferrer = "1.0"

    // Analytics
    object segment {
        const val core = "4.5.0-beta.0"
        const val amplitude = "3.0.2"
        const val adjust = "0.3.3"
        const val braze = "2.5.1"
    }

    const val adjust = "4.18.4"
    const val braze = "3.7.1"

    const val zendeskChat = "2.0.0"
    const val zendeskMessaging = "4.2.1"

    // Debug
    const val stetho = "1.5.1"
    const val canary = "2.2"
    const val instabug = "9.0.5"

    // Tools
    const val lint = "26.3.1"
    const val ktlint = "0.36.0"

    // Test
    const val jUnitPlatform = "1.4.0"
    const val jUnit = "5.5.2"
    const val mockk = "1.9.3"
    const val robolectric = "4.2"
    const val assertk = "0.19"
    const val spoon = "1.7.1"
    const val scrimage = "4.0.3"
}
