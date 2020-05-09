@file:Suppress("ClassName", "unused", "SpellCheckingInspection")

package com.iziwork.buildsrc

// @formatter:off
object Dependencies {
    val kotlin = arrayOf(
        "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}",
        "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin}"
    )
    const val kotlinHtml = "org.jetbrains.kotlinx:kotlinx-html-jvm:${Versions.kotlinHtml}"
    const val kotlinExperimental = true
    val kotlinExperimentalFeatures = arrayOf("parcelize")

    object androidX {
        // Functional commons
        val commons = arrayOf(
            "androidx.annotation:annotation:${Versions.androidX.annotations}",
            "androidx.appcompat:appcompat:${Versions.androidX.appcompat}",
            "androidx.collection:collection-ktx:${Versions.androidX.collection}",
            "androidx.core:core-ktx:${Versions.androidX.core}",
            "androidx.legacy:legacy-support-v4:${Versions.androidX.legacySupport}"
        )

        // UI commons
        val ui = arrayOf(
            "androidx.activity:activity-ktx:${Versions.androidX.activity}",
            "androidx.cardview:cardview:${Versions.androidX.cardView}",
            "androidx.constraintlayout:constraintlayout:${Versions.androidX.constraintLayout}",
            "androidx.coordinatorlayout:coordinatorlayout:${Versions.androidX.coordinatorlayout}",
            "androidx.fragment:fragment-ktx:${Versions.androidX.fragment}",
            // "androidx.palette:palette-ktx:${Versions.androidX.palette}",
            "androidx.recyclerview:recyclerview:${Versions.androidX.recycler}",
            "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.androidX.swipeRefreshLayout}",
            "androidx.vectordrawable:vectordrawable:${Versions.androidX.vectorDrawable}",
            "androidx.viewpager:viewpager:${Versions.androidX.viewPager}"
        )

        object lifecycle {
            val commons = arrayOf(
                "androidx.lifecycle:lifecycle-extensions:${Versions.androidX.lifecycle}",
                "androidx.lifecycle:lifecycle-reactivestreams-ktx:${Versions.androidX.lifecycle}",
                "androidx.lifecycle:lifecycle-common-java8:${Versions.androidX.lifecycle}"
            )
            const val compiler =
                "androidx.lifecycle:lifecycle-compiler:${Versions.androidX.lifecycle}"
        }

        object navigation {
            val commons = arrayOf(
                "androidx.navigation:navigation-fragment-ktx:${Versions.androidX.navigation}",
                "androidx.navigation:navigation-ui-ktx:${Versions.androidX.navigation}"
            )
        }

        object room {
            val commons = arrayOf(
                "androidx.room:room-runtime:${Versions.androidX.room}",
                "androidx.room:room-rxjava2:${Versions.androidX.room}"
            )
            const val compiler = "androidx.room:room-compiler:${Versions.androidX.room}"
            const val test = "androidx.room:room-testing:${Versions.androidX.room}"
        }

        object workManager {
            val commons = arrayOf(
                "androidx.work:work-runtime-ktx:${Versions.androidX.workManager}",
                "androidx.work:work-rxjava2:${Versions.androidX.workManager}"
            )
            const val firebase = "androidx.work.work:work-firebase:${Versions.androidX.workManager}"
            const val test = "androidx.work.work:work-testing:${Versions.androidX.workManager}"
        }

        val camera = arrayOf(
            "androidx.camera:camera-core:${Versions.androidX.camera}",
            "androidx.camera:camera-camera2:${Versions.androidX.camera}",
            "androidx.camera:camera-lifecycle:${Versions.androidX.camera}",
            "androidx.camera:camera-view:${Versions.androidX.cameraView}",
            "androidx.camera:camera-extensions:${Versions.androidX.cameraView}"
        )

        val test = arrayOf(
            // see https://developer.android.com/training/testing/set-up-project#gradle-dependencies
            // Core library
            "androidx.test:core:${Versions.androidX.test.core}",

            // AndroidJUnitRunner and JUnit Rules
            "androidx.test:runner:${Versions.androidX.test.runner}",
            "androidx.test:rules:${Versions.androidX.test.rules}",

            // Assertions
            "androidx.test.ext:junit:${Versions.androidX.test.junit}",
            "androidx.test.ext:truth:${Versions.androidX.test.truth}",
            "com.google.truth:truth:0.42",

            // Espresso dependencies
            "androidx.test.espresso:espresso-core:${Versions.androidX.test.espresso}",
            "androidx.test.espresso:espresso-contrib:${Versions.androidX.test.espresso}",
            "androidx.test.espresso:espresso-intents:${Versions.androidX.test.espresso}",
            "androidx.test.espresso:espresso-accessibility:${Versions.androidX.test.espresso}",
            "androidx.test.espresso:espresso-web:${Versions.androidX.test.espresso}",
            "androidx.test.espresso.idling:idling-concurrent:${Versions.androidX.test.espresso}",

            // The following Espresso dependency can be either "implementation"
            // or "androidTestImplementation", depending on whether you want the
            // dependency to appear on your APK"s compile classpath or the test APK
            // classpath.
            "androidx.test.espresso:espresso-idling-resource:${Versions.androidX.test.espresso}"
        )

        val fragmentTest = "androidx.fragment:fragment-testing:${Versions.androidX.fragment}"
    }

    const val materialComponents =
        "com.google.android.material:material:${Versions.materialComponents}"

    object dagger {
        const val commons = "com.google.dagger:dagger:${Versions.dagger}"
        const val compiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"

        object android {
            val commons = arrayOf(
                "com.google.dagger:dagger-android:${Versions.dagger}",
                "com.google.dagger:dagger-android-support:${Versions.dagger}"
            )
            const val compiler = "com.google.dagger:dagger-android-processor:${Versions.dagger}"
        }

        object assistedInject {
            const val commons =
                "com.squareup.inject:assisted-inject-annotations-dagger2:${Versions.assistedInject}"
            const val compiler =
                "com.squareup.inject:assisted-inject-processor-dagger2:${Versions.assistedInject}"
        }
    }

    object rx {
        val commons = arrayOf(
            "io.reactivex.rxjava2:rxjava:${Versions.rx.java}",
            "io.reactivex.rxjava2:rxkotlin:${Versions.rx.kotlin}"
        )
        const val android = "io.reactivex.rxjava2:rxandroid:${Versions.rx.android}"
    }

//    val retrofit = arrayOf(
//        "com.squareup.retrofit2:retrofit:${Versions.retrofit}",
//        "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}",
//        "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}",
//        "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"
//    )

    object moshi {
        val lib = arrayOf(
            "com.squareup.moshi:moshi:${Versions.moshi}",
            // "com.squareup.moshi:moshi-kotlin:${Versions.moshi}",
            "com.squareup.moshi:moshi-adapters:${Versions.moshi}"
        )
        const val codeGen = "com.squareup.moshi:moshi-kotlin-codegen:${Versions.moshi}"
    }

    object parse {
        val core = arrayOf(
            "com.github.parse-community.Parse-SDK-Android:parse:${Versions.parse.core}",
            "com.github.parse-community.Parse-SDK-Android:ktx:${Versions.parse.core}"
        )
        const val fcm = "com.github.parse-community.Parse-SDK-Android:fcm:${Versions.parse.core}"
        const val facebook =
            "com.github.badoualy:ParseFacebookUtils-Android:${Versions.parse.facebook}"
    }

    // Log
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"

    const val applanga = "com.applanga.android:Applanga:${Versions.applanga}"

    object facebook {
        const val login = "com.facebook.android:facebook-login:${Versions.facebook}"
    }

    // UI libs
    object glide {
        val commons = arrayOf(
            "com.github.bumptech.glide:glide:${Versions.glide}",
            "com.github.bumptech.glide:okhttp3-integration:${Versions.glide}"
        )
        const val compiler = "com.github.bumptech.glide:compiler:${Versions.glide}"
    }

    const val shimmer = "com.facebook.shimmer:shimmer:${Versions.shimmer}"
    val materialDialogs = arrayOf(
        "com.afollestad.material-dialogs:core:${Versions.materialDialogs}",
        "com.afollestad.material-dialogs:input:${Versions.materialDialogs}"
    )
    const val spinnerDatePicker =
        "com.github.badoualy:SpinnerDatePicker:${Versions.spinnerDatePicker}"
    const val photoView = "com.github.chrisbanes:PhotoView:${Versions.photoView}"
    const val androidSvg = "com.caverock:androidsvg-aar:${Versions.androidSvg}"
    const val markdown = "io.noties.markwon:core:${Versions.markdown}"
    const val recyclerAnimators = "jp.wasabeef:recyclerview-animators:${Versions.recyclerAnimators}"
    const val shapeOfView = "com.github.florent37:shapeofview:${Versions.shapeOfView}"
    const val lottie = "com.airbnb.android:lottie:${Versions.lottie}"

    // Fabric/Firebase
    const val crashlytics = "com.crashlytics.sdk.android:crashlytics:${Versions.crashlytics}@aar"

    object firebase {
        const val core = "com.google.firebase:firebase-core:${Versions.firebase}"
        const val messaging = "com.google.firebase:firebase-messaging:${Versions.firebaseCM}"
    }

    // Play services
    const val playCore = "com.google.android.play:core:${Versions.playCore}"

    object playServices {

        const val places =
            "com.google.android.libraries.places:places:${Versions.playServices.places}"

        object auth {
            const val apiPhone =
                "com.google.android.gms:play-services-auth-api-phone:${Versions.playServices.auth.apiPhone}"
        }
    }

    const val installReferrer =
        "com.android.installreferrer:installreferrer:${Versions.installReferrer}"

    // Analytics
    val segment = arrayOf(
        "com.segment.analytics.android:analytics:${Versions.segment.core}",
        "com.segment.analytics.android.integrations:amplitude:${Versions.segment.amplitude}",
        "com.segment.analytics.android.integrations:adjust:${Versions.segment.adjust}",
        "com.appboy:appboy-segment-integration:${Versions.segment.braze}"
    )
    const val adjust = "com.adjust.sdk:adjust-android:${Versions.adjust}"
    const val braze = "com.appboy:android-sdk-ui:${Versions.braze}"

    // Debug
    val stetho = arrayOf(
        "com.facebook.stetho:stetho:${Versions.stetho}",
        "com.facebook.stetho:stetho-okhttp3:${Versions.stetho}"
    )

    const val canary = "com.squareup.leakcanary:leakcanary-android:${Versions.canary}"

    const val instabug = "com.instabug.library:instabug:${Versions.instabug}"

    val zendesk = arrayOf(
        "com.zendesk:chat:${Versions.zendeskChat}",
        "com.zendesk:messaging:${Versions.zendeskMessaging}"
    )

    // Tools
    object lint {
        const val core = "com.android.tools.lint:lint:${Versions.lint}"
        const val api = "com.android.tools.lint:lint-api:${Versions.lint}"
        const val checks = "com.android.tools.lint:lint-checks:${Versions.lint}"
        const val tests = "com.android.tools.lint:lint-tests:${Versions.lint}"
    }

    // Test
    object test {
        val jUnit = arrayOf(
            "org.junit.jupiter:junit-jupiter-api:${Versions.jUnit}",
            "org.junit.jupiter:junit-jupiter-engine:${Versions.jUnit}",
            "org.junit.jupiter:junit-jupiter-params:${Versions.jUnit}",

            "org.junit.platform:junit-platform-runner:${Versions.jUnitPlatform}",

            "org.junit.vintage:junit-vintage-engine:${Versions.jUnit}"
        )

        val kotlin = arrayOf(
            "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin}",
            "org.jetbrains.kotlin:kotlin-test:${Versions.kotlin}",
            "org.jetbrains.kotlin:kotlin-test-junit:${Versions.kotlin}"
        )

        const val mockk = "io.mockk:mockk:${Versions.mockk}"
        const val robolectric = "org.robolectric:robolectric:${Versions.robolectric}"
        const val assertk = "com.willowtreeapps.assertk:assertk-jvm:${Versions.assertk}"
    }

    const val spoon = "com.squareup.spoon:spoon-client:${Versions.spoon}"
    const val barista = "com.schibsted.spain:barista:${Versions.androidX.test.barista}"
    const val scrimage = "com.sksamuel.scrimage:scrimage-core:${Versions.scrimage}"
}
