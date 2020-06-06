package com.movie.xutil

import android.os.Build
import android.view.View
import android.widget.ProgressBar
import com.google.android.material.snackbar.Snackbar


fun ProgressBar.show() {
    visibility = View.VISIBLE
}

fun ProgressBar.hide() {
    visibility = View.GONE
}

fun View.showSnackbar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).also { snackbar ->
        snackbar.setAction("Ok") {
            snackbar.dismiss()
        }
    }.show()
}

fun View.toTransitionGroup() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
    this to transitionName
} else {
    null
}
