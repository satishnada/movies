package com.movie.xutil

import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.movie.R


fun Context.showLogoutAlertDialog(onYes: () -> Unit): AlertDialog =
    AlertDialog.Builder(this)
        .setIcon(ContextCompat.getDrawable(this, R.drawable.ic_app_logo))
        .setTitle("Are you sure to want logout?")
        .setPositiveButton("Yes") { _, _ ->
            onYes.invoke()
        }
        .setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        .show()