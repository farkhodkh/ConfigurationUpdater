package com.farkhodkhaknazarov.configurationupdater.feature.remote.config.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.farkhodkhaknazarov.configurationupdater.feature.remote.config.executor.RemoteTerminalConfigExecutor

open class RemoteConfigFactory(val executorTerminal: RemoteTerminalConfigExecutor) :
    WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when (workerClassName) {
            RemoteConfigWorker::class.java.name -> {
                RemoteConfigWorker(appContext, workerParameters, executorTerminal)
            }
            else -> null
        }
    }
}