package com.movie.xutil

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.gson.Gson

inline fun <reified R> String.asJsonModel(): R = Gson().fromJson(this, R::class.java)

fun hideKeyboard(view: View) {
    val imm: InputMethodManager? =
        view.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm?.hideSoftInputFromWindow(view.windowToken, 0)
}