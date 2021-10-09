package com.floriv.moviereferences.core.utils

import android.util.Log
import com.floriv.moviereferences.BuildConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.internal.immutableListOf
import okio.Buffer
import java.io.IOException
import java.io.PrintWriter
import java.io.StringWriter
import java.util.*

internal class LoggingInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val t1 = System.nanoTime()
        var requestLog = String.format(
            "Sending request %s on %s%n%s",
            request.url,
            chain.connection(),
            request.headers
        )
        val methods = immutableListOf("put", "post", "patch")
        if (request.method.lowercase() in methods) {
            requestLog = "\n $requestLog \n ${bodyToString(request)}"
            logDebug("Response body ${request.body.toString()}")
        }
        logDebug("request \n $requestLog")
        val response = chain.proceed(request)
        val t2 = System.nanoTime()
        val responseLog = String.format(
            Locale.ENGLISH,
            "Received status Response for %s in %.1fms%n%s",
            response.request.url,
            (t2 - t1) / 1e6,
            response.headers
        )
        logDebug(responseLog)
        var bodyString = "-"
        response.body?.let {
            bodyString = it.string()
            logDebug("Response code ${response.code}")
            logDebug("Response body $bodyString")

        }
        return response.newBuilder()
            .body(bodyString.toResponseBody(response.body!!.contentType()))
            .build()
    }

    companion object {
        const val TAG = "LoggingInterceptor"

        private fun bodyToString(request: Request): String {
            try {
                val copy = request.newBuilder().build()
                val buffer = Buffer()
                copy.body?.writeTo(buffer)
                return buffer.readUtf8()
            } catch (e: IOException) {
                val sw = StringWriter()
                e.printStackTrace(PrintWriter(sw))
                val exceptionAsString = sw.toString()
                logDebug("StackTrace $exceptionAsString")
            }
            return "-"
        }

        private fun logDebug(msg: String) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, msg)
            }
        }
    }
}