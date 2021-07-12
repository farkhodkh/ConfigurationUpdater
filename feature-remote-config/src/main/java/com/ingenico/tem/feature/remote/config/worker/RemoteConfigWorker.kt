package com.farkhodkhaknazarov.configurationupdater.feature.remote.config.worker

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.farkhodkhaknazarov.configurationupdater.feature.remote.config.R
import com.farkhodkhaknazarov.configurationupdater.feature.remote.config.executor.RemoteTerminalConfigExecutor
import com.farkhodkhaknazarov.configurationupdater.feature.remote.config.model.ConfigurationModel
import com.farkhodkhaknazarov.configurationupdater.feature.remote.config.util.FileBrowser
import com.farkhodkhaknazarov.configurationupdater.feature.remote.config.util.ParametersParser

class RemoteConfigWorker(
    appContext: Context,
    workerParams: WorkerParameters,
    private val executorTerminal: RemoteTerminalConfigExecutor
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        if (ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return Result.failure()
        }

        val folderPath = applicationContext.getString(R.string.browse_folder)

        FileBrowser(applicationContext, folderPath)
            .getConfigFiles()
            ?.forEach { file ->
                val configuration: ConfigurationModel =
                    file.inputStream()
                        .use { params -> ParametersParser(applicationContext.applicationInfo.packageName).parse(params) }

                executorTerminal.execute(configuration)
                file.delete()
            }
        return Result.success()
    }
}