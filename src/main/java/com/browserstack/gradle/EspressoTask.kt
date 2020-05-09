package com.browserstack.gradle

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.json.JSONObject
import java.nio.file.Path
import java.util.concurrent.TimeoutException
import javax.inject.Inject

open class EspressoTask @Inject constructor(
    host: String,
    variantBaseName: String,
    @Input val extension: BrowserStackConfigExtension
) : BrowserStackTask(
    host = host,
    variantBaseName = variantBaseName,
    username = extension.username,
    accessKey = extension.accessKey
) {

    override fun verifyParams() {
        super.verifyParams()
        require(extension.devices.isNotEmpty()) { "You must specify at least one device (devices)" }
    }

    @TaskAction
    open fun uploadAndExecuteTest() {
        verifyParams()

        // First upload app
        val apkFiles = getApkCollection(findTestApk = true)
        val app = uploadApp(Constants.APP_AUTOMATE_UPLOAD_PATH, apkFiles.debugApk)

        // Then upload and execute test suite
        val testSuite = uploadTestSuite(apkFiles.testApk ?: error("testApk not found"))
        val buildId = executeTestSuite(app, testSuite)

        if (extension.pollTestsResults) {
            // Hang process until the build is done
            waitForBuildDone(buildId)
                .also { println("Build result:\n${it.toString(2)}") }
        }
    }

    private fun constructBuildParams(app: String, testSuite: String): String {
        return constructDefaultBuildParams().apply {
            put("app", app)
            put("testSuite", testSuite)

            put("devices", extension.devices)
            put("class", extension.classes)
            put("package", extension.packages)
            put("size", extension.sizes)
            put("annotation", extension.annotations)
            put("otherApps", extension.otherApps)
            put("video", extension.video)
            put("deviceLogs", extension.deviceLogs)
            put("networkLogs", extension.networkLogs)
            put("local", extension.local)
            put("localIdentifier", extension.localIdentifier)
            put("networkProfile", extension.networkProfile)
            put("callbackURL", extension.callbackURL)
            put("timezone", extension.timeZone)
            put("appStoreConfiguration", extension.appStoreConfigurationMap)
            put("enableSpoonFramework", extension.enableSpoonFramework)
            put("disableAnimations", extension.disableAnimations)
            put("allowDeviceMockServer", extension.allowDeviceMockServer)
            put("customBuildName", extension.customBuildName)
            put("customBuildNotifyURL", extension.customBuildNotifyURL)
            put("project", extension.projectName)
            put("geoLocation", extension.geoLocation)
            put("language", extension.language)
            put("locale", extension.locale)
            put("deviceOrientation", extension.deviceOrientation)
        }.toString()
    }

    private fun uploadTestSuite(testApkPath: Path): String {
        return uploadApk(Constants.TEST_SUITE_UPLOAD_PATH, testApkPath).getString("test_url")
            .also { println("test_url: $it") }
    }

    private fun executeTestSuite(app: String, testSuite: String): String {
        val body = constructBuildParams(
            app,
            testSuite
        ).toRequestBody("application/json".toMediaTypeOrNull())
        val json = post(Constants.BUILD_PATH, body)
        return json.getString("build_id")
            .also { println("View build status at ${Constants.APP_AUTOMATE_HOST}/dashboard/v2/builds/$it") }
    }

    private fun waitForBuildDone(buildId: String): JSONObject {
        val startTime = System.currentTimeMillis()

        Thread.sleep(Constants.TEST_RESULT_POLL_INITIAL_DELAY)
        while (true) {
            try {
                val json = getBuildDetails(buildId)
                val status = json.optString("status")
                if (status in arrayOf("done", "failed")) {
                    return json
                }
                println("Build status: $status")
            } catch (e: Exception) {
            }

            if (System.currentTimeMillis() - startTime > Constants.TEST_RESULT_POLL_TIMEOUT) {
                throw TimeoutException("Waited ${Constants.TEST_RESULT_POLL_TIMEOUT} minutes, timeout")
            }
            Thread.sleep(Constants.TEST_RESULT_POLL_INTERVAL)
        }
    }

    private fun getBuildDetails(buildId: String): JSONObject {
        return get(Constants.BUILDS_PATH + "/" + buildId)
    }
}
