package com.farkhodkhaknazarov.configurationupdater.feature.remote.config.util

import java.io.File

internal fun File.getConfigFiles(): List<File>? =
    listFiles()
        ?.filter {
            it.name.matches(FileBrowser.CONFIG_FILE_MASK)
        }