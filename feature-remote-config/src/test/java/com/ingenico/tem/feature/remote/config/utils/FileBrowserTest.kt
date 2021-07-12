package com.farkhodkhaknazarov.configurationupdater.feature.remote.config.utils

import com.farkhodkhaknazarov.configurationupdater.feature.remote.config.util.FileBrowser
import org.junit.Assert
import org.junit.Test
import java.io.File

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class FileBrowserTest {

    @Test
    fun `config template file presents`() {
        val dir = FileBrowserTest::class.java.getResource("/config")?.let { File(it.toURI()) }

        Assert.assertNotNull(dir)

        val filesCount = dir?.let {
            it.listFiles()
            .filter {
                it.name.matches(FileBrowser.CONFIG_FILE_MASK)
            }.size
        } ?: 0

        Assert.assertTrue(filesCount > 0)
    }
}