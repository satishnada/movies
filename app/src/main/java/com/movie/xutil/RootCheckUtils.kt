package com.movie.xutil

import java.io.DataOutputStream
import java.io.File

object RootCheckUtils {
    fun checkRoot() = try {
        val p = Runtime.getRuntime().exec("su", null, File("/"))
        val os = DataOutputStream(p.outputStream)
        os.writeBytes("pwd\n")
        os.writeBytes("exit\n")
        os.flush()
        p.waitFor()
        p.destroy()
        true
    } catch (e: Exception) {
        false
    }
}