package com.movie.data.remote

import android.content.Context
import com.google.gson.GsonBuilder
import com.movie.BuildConfig
import com.movie.data.preferences.PreferenceProvider
import com.movie.data.remote.interceptor.MyApiRequestInterceptor
import com.movie.data.remote.interceptor.NetworkConnectionInterceptor
import com.movie.data.remote.interceptor.TMDBRequestInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


fun createLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
    level = if (BuildConfig.DEBUG)
        HttpLoggingInterceptor.Level.BODY
    else
        HttpLoggingInterceptor.Level.NONE
}

fun createConnectionInterceptor(context: Context): NetworkConnectionInterceptor =
    NetworkConnectionInterceptor(context = context)

fun createTMDBRequestInterceptor(): TMDBRequestInterceptor =
    TMDBRequestInterceptor()

fun createMyApiRequestInterceptor(preferenceProvider: PreferenceProvider): MyApiRequestInterceptor =
    MyApiRequestInterceptor(preferenceProvider)

fun createHttpClient(
    httpLoggingInterceptor: HttpLoggingInterceptor,
    networkConnectionInterceptor: NetworkConnectionInterceptor,
    myApiRequestInterceptor: MyApiRequestInterceptor
): OkHttpClient {
    val client = OkHttpClient.Builder()

    client.readTimeout(90L, TimeUnit.SECONDS)
    client.writeTimeout(90L, TimeUnit.SECONDS)
    client.connectTimeout(30L, TimeUnit.SECONDS)

    client.addInterceptor(httpLoggingInterceptor)
    client.addInterceptor(networkConnectionInterceptor)
    client.addInterceptor(myApiRequestInterceptor)

    return client.build()
}

fun createTMDBHttpClient(
    httpLoggingInterceptor: HttpLoggingInterceptor,
    networkConnectionInterceptor: NetworkConnectionInterceptor,
    TMDBRequestInterceptor: TMDBRequestInterceptor
): OkHttpClient {
    val client = OkHttpClient.Builder()

    client.readTimeout(90L, TimeUnit.SECONDS)
    client.writeTimeout(90L, TimeUnit.SECONDS)
    client.connectTimeout(30L, TimeUnit.SECONDS)

    client.addInterceptor(httpLoggingInterceptor)
    client.addInterceptor(networkConnectionInterceptor)
    client.addInterceptor(TMDBRequestInterceptor)

    return client.build()
}

inline fun <reified SERVICE> createWebService(
    okHttpClient: OkHttpClient,
    baseUrl: String
): SERVICE {
    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .client(okHttpClient)
        .build()
    return retrofit.create(SERVICE::class.java)
}