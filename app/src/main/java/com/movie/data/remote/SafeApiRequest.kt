package com.movie.data.remote

import com.movie.base.BaseResponse
import com.movie.xutil.ApiException
import com.movie.xutil.eLog
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

abstract class SafeApiRequest {
    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): T {
        val response = call.invoke()
        if (response.isSuccessful) {
            try {
                val base = (response.body()!! as BaseResponse)
                base.error?.let {
                    if (it) {
                        val message = StringBuilder()
                        message.append(base.errorCode).append(" ").append(base.message)
                        throw ApiException(message.toString())
                    }
                }
            } catch (e: Exception) {
                val message = StringBuilder()
                message.append("An unknown error occurred")
                throw ApiException(message.toString())
            }
            return response.body()!!
        } else {
            val error = response.errorBody()?.string()
            val message = StringBuilder()
            error?.let {
                try {
                    message.append(JSONObject(it).getString("message"))
                    message.append("\n")
                } catch (e: JSONException) {
                    eLog<SafeApiRequest>(e.message!!)
                }
            }
            message.append("Error Code: ${response.code()}")
            throw ApiException(message.toString())
        }
    }

    suspend fun <T : Any> tmdbApiRequest(call: suspend () -> Response<T>): T {
        val response = call.invoke()
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            val error = response.errorBody()?.string()
            val message = StringBuilder()
            error?.let {
                try {
                    message.append(JSONObject(it).getString("message"))
                    message.append("\n")
                } catch (e: JSONException) {
                    eLog<SafeApiRequest>(e.message!!)
                }
            }
            message.append("Error Code: ${response.code()}")
            throw ApiException(message.toString())
        }
    }
}