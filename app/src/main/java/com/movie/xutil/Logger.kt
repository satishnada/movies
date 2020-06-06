package com.movie.xutil

import android.util.Log
import com.movie.BuildConfig.DEBUG

var LOG_ENABLED = true

inline fun <reified TAG> dLog(message: String) {
    if (LOG_ENABLED) {
        Log.d(TAG::class.java.simpleName, buildMsg(message))
    }
}

inline fun <reified TAG> eLog(message: String) {
    if (LOG_ENABLED) {
        Log.e(TAG::class.java.simpleName, buildMsg(message))
    }
}

inline fun <reified TAG> eLog(message: String, ex: Exception) {
    if (LOG_ENABLED) {
        Log.e(TAG::class.java.simpleName, buildMsg(message), ex)
    }
}

inline fun <reified TAG> iLog(message: String) {
    if (LOG_ENABLED) {
        Log.i(TAG::class.java.simpleName, buildMsg(message))
    }
}

inline fun <reified TAG> wLog(message: String) {
    if (LOG_ENABLED) {
        Log.w(TAG::class.java.simpleName, buildMsg(message))
    }
}

inline fun <reified TAG> vLog(message: String) {
    if (LOG_ENABLED) {
        Log.v(TAG::class.java.simpleName, buildMsg(message))
    }
}

fun buildMsg(msg: String): String {
    val buffer = StringBuilder()
    if (DEBUG) {
        val stackTraceElement = Thread.currentThread().stackTrace[4]
        buffer.append("[Thread: ${Thread.currentThread().name}")
        buffer.append(", Class: ${stackTraceElement.fileName}")
        buffer.append(", Line: ${stackTraceElement.lineNumber}")
        buffer.append(", Method: ${stackTraceElement.methodName}()]")
    }
    buffer.append("\nLog: ")
    buffer.append(msg)
    return buffer.toString()
}