package com.farkhodkhaknazarov.configurationupdater

import android.app.Application
import androidx.work.Configuration
import androidx.work.DelegatingWorkerFactory
import androidx.work.WorkManager
import com.farkhodkhaknazarov.configurationupdater.feature.remote.config.worker.RemoteConfigScheduler
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {
    @Inject
    lateinit var workerFactory: DelegatingWorkerFactory

    @Inject
    lateinit var workerScheduler: RemoteConfigScheduler

    override fun onCreate() {
        super.onCreate()

        initWorkManager()
    }

    private fun initWorkManager() {
        WorkManager.initialize(
            this,
            Configuration.Builder()
                .setWorkerFactory(workerFactory)
                .build()
        )

        workerScheduler.scheduleWorker(this)
    }
}