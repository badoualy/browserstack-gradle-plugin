package com.browserstack.gradle

import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.json.JSONObject
import java.io.File
import javax.inject.Inject

open class DownloadEspressoTestsResultsTask @Inject constructor(
    host: String,
    variantBaseName: String,
    @Input val extension: BrowserStackConfigExtension
) : BrowserStackTask(
    host = host,
    variantBaseName = variantBaseName,
    username = extension.username,
    accessKey = extension.accessKey
) {

    private val timestampRegex = "^[0-9]+_".toRegex()

    @TaskAction
    open fun downloadTestResults() {
        val buildId = getLatestBuildId(getProjectDetails()) ?: return
        downloadTestsResults(getBuildDetails(buildId))
    }

    private fun getProjectDetails(): JSONObject {
        return get(Constants.PROJECT_PATH + "/" + extension.projectName)
    }

    private fun getBuildDetails(buildId: String): JSONObject {
        return get(Constants.BUILDS_PATH + "/" + buildId)
    }

    private fun getSessionDetails(buildId: String, sessionId: String): JSONObject {
        return get(Constants.BUILDS_PATH + "/" + buildId + "/sessions/" + sessionId)
    }

    private fun getLatestBuildId(project: JSONObject): String? {
        return project.optJSONArray("builds")?.optJSONObject(0)?.optString("build_id").also {
            println("latestBuildId: $it")
        }
    }

    private fun downloadTestsResults(buildDetails: JSONObject) {
        val testResultDir = File(project.buildDir, extension.testResultsDestination).apply {
            deleteRecursively()
            mkdirs()
        }

        val devices = buildDetails.optJSONObject("devices") ?: return
        val entries = devices.keySet().map { it to devices.optJSONObject(it) }.toMap()
        println("Devices: ${entries.keys.joinToString()}")

        entries.forEach { (deviceName, deviceObj) ->
            // Create a dir for the device
            val deviceDirName = deviceName
                .replace('.', '_')
                .replace(Constants.FILE_NAME_PATTERN, "")
            val deviceDir = File(testResultDir, deviceDirName).also { it.mkdirs() }

            // Download xml result file
            val reportUrl = deviceObj.optString("test_report") ?: return@forEach
            download(reportUrl, File(deviceDir, "${deviceDirName}_result.xml"))

            // Download misc content (logs, screenshots, ...)
            // First get session details
            val sessionDetails = getSessionDetails(
                buildId = buildDetails.getString("build_id"),
                sessionId = deviceObj.getString("session_id")
            )
            val testDetails = sessionDetails.optJSONObject("test_details") ?: return@forEach

            downloadScreenshots(testDetails, deviceDir)
            // TODO: logs, network logs, ...
        }
    }

    private fun downloadScreenshots(testDetails: JSONObject, deviceDir: File) {
        // Get urls
        val classByNameMap =
            testDetails.keySet().map { it to testDetails.optJSONObject(it) }.toMap()
        val screenshotsByFunctionByClassMap = classByNameMap.mapValues { (_, classObj) ->
            val functionByNameMap =
                classObj.keySet().map { it to classObj.getJSONObject(it) }.toMap()

            functionByNameMap
                .mapValues { (_, functionObj) ->
                    functionObj.optJSONArray("screenshots").map {
                        // Weird bug, api returns some " in the url
                        it.toString().replace("\"", "")
                    }
                }
        }

        // Download
        screenshotsByFunctionByClassMap.forEach { (className, screenshotsByFunctionMap) ->
            screenshotsByFunctionMap.forEach { (functionName, screenshots) ->
                val dir =
                    getScreenshotDir(deviceDir, className.substringAfterLast('.'), functionName)
                screenshots.forEach { screenshotUrl ->
                    var name = screenshotUrl.substringAfterLast('/').substringBefore('?')
                    if (extension.removeTimestampFromScreenshots) {
                        name = name.replace(timestampRegex, "")
                    }
                    download(screenshotUrl, File(dir, name))
                }
            }
        }
    }

    private fun getScreenshotDir(dir: File, className: String, functionName: String): File {
        val groupedByClass = extension.testResultsGroupedByClass
        val groupedByFunction = extension.testResultsGroupedByFunction
        return when {
            groupedByClass && groupedByFunction -> File(File(dir, className), functionName)
            groupedByClass -> File(dir, className)
            groupedByFunction -> File(dir, functionName)
            else -> File(dir, className)
        }.apply { mkdirs() }
    }
}
