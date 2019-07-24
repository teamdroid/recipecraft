package ru.teamdroid.recipecraft.util

import android.content.Context
import java.io.*
import java.net.URL
import java.util.*
import javax.inject.Inject

class FileUtils @Inject constructor(val context: Context) {

    fun saveFileByUrl(url: URL): String {

        val inputSteam: InputStream = BufferedInputStream(url.openStream())
        val out = ByteArrayOutputStream()
        val buffer = ByteArray(1024)
        var n = inputSteam.read(buffer)

        while (n != -1) {
            out.write(buffer, 0, n)
            n = inputSteam.read(buffer)
        }

        out.close()
        inputSteam.close()

        val filePath = context.filesDir.path + File.separator + Date().time

        FileOutputStream(filePath).apply {
            write(out.toByteArray())
            close()
        }

        return filePath
    }
}