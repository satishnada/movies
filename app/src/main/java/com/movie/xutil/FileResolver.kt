package com.movie.xutil

import android.app.Activity
import android.net.Uri
import android.provider.MediaStore
import java.io.File
import java.io.InputStream

@Suppress("deprecation")
fun Activity.getFilePath(selectedImage: Uri?): String? {
    var filePath: String? = null
    if (selectedImage == null) {
        return filePath
    }
    val filePathColumn =
        arrayOf(MediaStore.Images.Media.DATA)
    val cursor =
        contentResolver.query(selectedImage, filePathColumn, null, null, null)
    cursor?.let {
        it.moveToFirst()
        val columnIndex = it.getColumnIndex(filePathColumn[0])
        filePath = it.getString(columnIndex)
        it.close()
        return filePath
    }
    cursor?.close()
    return filePath
}

fun File.copyInputStreamToFile(inputStream: InputStream) {
    this.outputStream().use { fileOut ->
        inputStream.copyTo(fileOut)
    }
}