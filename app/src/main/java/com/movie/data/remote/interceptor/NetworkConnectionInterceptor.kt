package com.movie.data.remote.interceptor

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.movie.R
import com.movie.xutil.NoInternetException
import okhttp3.Interceptor
import okhttp3.Response
import java.net.SocketTimeoutException
import java.net.UnknownHostException


@Suppress("DEPRECATION")
class NetworkConnectionInterceptor(val context: Context) : Interceptor {

    private val applicationContext = context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {
        try {
            if (!isInternetAvailable())
                throw NoInternetException(context.getString(R.string.internet_connection_error))
            return chain.proceed(chain.request())
        } catch (e: UnknownHostException) {
            throw NoInternetException(context.getString(R.string.service_not_available_error))
        } catch (e: SocketTimeoutException) {
            throw NoInternetException(context.getString(R.string.request_time_out_error))
        } catch (e: Exception) {
            throw NoInternetException(context.getString(R.string.proper_internet_connection_error))
        }
    }

    private fun isInternetAvailable(): Boolean {
        var result = false
        val connectivityManager =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        connectivityManager?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                it.getNetworkCapabilities(connectivityManager.activeNetwork)?.apply {
                    result = when {
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                        else -> false
                    }
                }
            } else {
                val activeNetwork = connectivityManager.activeNetworkInfo
                return activeNetwork != null && activeNetwork.isConnected
            }
        }
        return result
    }

}