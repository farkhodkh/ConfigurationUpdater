package com.farkhodkhaknazarov.configurationupdater.feature.remote.config.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import org.slf4j.LoggerFactory
import java.io.File

class FileBrowser(val context: Context, val folderPath: String) {
    private val logger by lazy { LoggerFactory.getLogger(this::class.java) }

    suspend fun getConfigFiles(): List<File>? {
        if (
            ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            logger.warn("Error. Read external storage permission required for reading configuration files")
            return null
        }

        return try {
            File(folderPath).getConfigFiles()
        } catch (ex: Exception) {
            logger.warn("Error while reading config files. Cause: {}", ex)
            return null
        }
    }


    companion object {
        val CONFIG_FILE_MASK = Regex("""^(\w*)?configurations-params-template(\.\w*)?\.xml""")
    }
}