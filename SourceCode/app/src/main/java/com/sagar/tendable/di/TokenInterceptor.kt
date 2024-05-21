package com.sagar.tendable.di

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import okio.Buffer
import okio.IOException

class TokenInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest: Request = chain.request()
        val response = chain.proceed(originalRequest)
        Log.e("interceptor", "[${originalRequest.method}]; ${response.code}; ${originalRequest.url}; ${
            bodyToString(
                originalRequest.body
            )
        }")
        return response
    }

    private fun bodyToString(request: RequestBody?): String? {
        return try {
            val buffer = Buffer()
            if (request != null) request.writeTo(buffer) else return ""
            buffer.readUtf8()
        } catch (e: IOException) {
            "did not work"
        }
    }
}