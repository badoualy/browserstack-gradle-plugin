package com.browserstack.gradle

import java.io.File
import java.util.concurrent.TimeUnit

object Constants {
    const val BROWSERSTACK_API_HOST = "https://api-cloud.browserstack.com"
    const val APP_LIVE_HOST = "https://app-live.browserstack.com"
    const val APP_AUTOMATE_HOST = "https://app-automate.browserstack.com"

    const val PROJECT_PATH = "/app-automate/espresso/projects"
    const val BUILD_PATH = "/app-automate/espresso/build"
    const val BUILDS_PATH = "/app-automate/espresso/builds"
    const val APP_AUTOMATE_UPLOAD_PATH = "/app-automate/upload"
    const val APP_LIVE_UPLOAD_PATH = "/app-live/upload"
    const val TEST_SUITE_UPLOAD_PATH = "/app-automate/espresso/test-suite"

    const val DEFAULT_VIDEO = true
    const val DEFAULT_DEVICE_LOGS = true
    const val DEFAULT_NETWORK_LOGS = false
    const val DEFAULT_LOCAL = false
    const val APP_SEARCH_MAX_DEPTH = 10

    val TEST_RESULT_POLL_INITIAL_DELAY = TimeUnit.SECONDS.toMillis(30)
    val TEST_RESULT_POLL_TIMEOUT = TimeUnit.MINUTES.toMillis(10)
    val TEST_RESULT_POLL_INTERVAL = TimeUnit.SECONDS.toMillis(15)

    val DEFAULT_TEST_RESULTS_DESTINATION = "test-results${File.separator}browserstack"

    val FILE_NAME_PATTERN = "[^a-zA-Z0-9\\s\\-_+]".toRegex()
}
