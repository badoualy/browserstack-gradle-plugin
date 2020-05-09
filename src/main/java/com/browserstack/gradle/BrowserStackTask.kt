package com.browserstack.gradle

import okhttp3.Authenticator
import okhttp3.Credentials
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.Response
import okhttp3.Route
import okhttp3.internal.closeQuietly
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.json.JSONObject
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.attribute.BasicFileAttributes
import java.util.ArrayList

abstract class BrowserStackTask(
    @Input val host: String,
    @Input val variantBaseName: String,
    @Input val username: String,
    @Input val accessKey: String
) : DefaultTask() {

    protected open fun verifyParams() {
        require(!username.isBlank()) { "Missing mandatory username" }
        require(!accessKey.isBlank()) { "Missing mandatory accessKey" }
    }

    protected fun constructDefaultBuildParams(): JSONObject {
        return JSONObject().apply {
            // for monitoring, not for external use
            put("browserstack.source", "gradlePlugin")
        }
    }

    protected fun uploadApp(httpPath: String, apkPath: Path): String {
        return uploadApk(httpPath, apkPath).getString("app_url")
            .also { println("app_url: $it") }
    }

    protected fun uploadApk(httpPath: String, apkPath: Path): JSONObject {
        val apkFile = apkPath.toFile().takeIf { it.exists() } ?: error("file not found $apkPath")
        val body = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart(
                "file",
                apkFile.name,
                apkFile.asRequestBody("application/vnd.android.package-archive".toMediaTypeOrNull())
            )
            // TODO: Allow custom_id
            .build()

        return post(httpPath, body)
    }

    protected fun post(httpPath: String, body: RequestBody): JSONObject {
        return Request.Builder()
            .url(host + httpPath)
            .post(body)
            .build()
            .let(::call)
    }

    protected fun get(httpPath: String): JSONObject {
        return Request.Builder()
            .url(host + httpPath)
            .get()
            .build()
            .let(::call)
    }

    protected fun download(url: String, destination: File) {
        println("Downloading $url to ${destination.path}")
        val request = Request.Builder()
            .url(url)
            .build()

        newOkHttpClient("", "").newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            destination.outputStream().use { fos ->
                response.body?.byteStream()?.copyTo(fos)
            }
            response.body?.closeQuietly()
        }
    }

    protected fun call(request: Request): JSONObject {
        return try {
            val response = newOkHttpClient(username, accessKey).newCall(request).execute()

            when (val responseCode = response.code.also { println("Response Code: $it") }) {
                200 -> JSONObject(response.body?.string().orEmpty())
                else -> throw IOException("Call failed, code $responseCode: ${response.message}")
            }.also { response.body?.closeQuietly() }
        } catch (e: Exception) {
            e.printStackTrace();
            throw e
        }
    }

    protected fun getApkCollection(
        findTestApk: Boolean = false
    ): ApkCollection {
        val appApkFiles: MutableList<Path> = ArrayList()
        val testApkFiles: MutableList<Path> = ArrayList()

        Files.find(
            project.buildDir.toPath(),
            Constants.APP_SEARCH_MAX_DEPTH,
            { filePath: Path, fileAttr: BasicFileAttributes -> isValidFile(filePath, fileAttr) }
        ).forEach { f: Path ->
            if (f.toString().endsWith("-androidTest.apk")) {
                testApkFiles.add(f)
            } else {
                appApkFiles.add(f)
            }
        }
        val debugApkPath =
            appApkFiles.findMostRecent().also { println("Most recent DebugApp apk: $it") }
                ?: error("Unable to find DebugApp apk")
        val testApkPath =
            testApkFiles.findMostRecent().also { println("Most recent TestApp apk: $it") }
                ?: run { if (findTestApk) error("unable to find TestApp apk") else null }

        return ApkCollection(
            debugApk = debugApkPath,
            testApk = testApkPath
        )
    }

    private fun isValidFile(
        filePath: Path,
        fileAttr: BasicFileAttributes
    ): Boolean {
        return fileAttr.isRegularFile && filePath.toString().endsWith(".apk") &&
            filePath.fileName.toString().contains(variantBaseName)
    }

    companion object {

        fun List<Path>.findMostRecent(): Path? = maxBy { it.toFile().lastModified() }

        fun newOkHttpClient(username: String, accessKey: String): OkHttpClient {
            return OkHttpClient.Builder()
                .authenticator(object : Authenticator {
                    override fun authenticate(route: Route?, response: Response): Request? {
                        val credential: String = Credentials.basic(username, accessKey)
                        return response.request
                            .newBuilder()
                            .header("Authorization", credential)
                            .build()
                    }
                })
                .build()
        }

        data class ApkCollection(
            val debugApk: Path,
            val testApk: Path?
        )
    }
}
