package com.movie.data.remote.interceptor

import com.movie.data.preferences.PreferenceProvider
import com.movie.xutil.AppConstants.Companion.PLATFORM
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class MyApiRequestInterceptor(val preferenceProvider: PreferenceProvider) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        //val originalUrl = originalRequest.url

        val requestBuilder = originalRequest.newBuilder()
            .addHeader("Platform", PLATFORM)

        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}