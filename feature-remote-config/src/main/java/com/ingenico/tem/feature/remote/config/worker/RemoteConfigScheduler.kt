package com.farkhodkhaknazarov.configurationupdater.feature.remote.config.worker

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.farkhodkhaknazarov.configurationupdater.core.model.SchedulerConfiguration
import com.farkhodkhaknazarov.configurationupdater.feature.persistence.api.SchedulerConfigurationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class RemoteConfigScheduler(private val schedulerConfigurationRepository: SchedulerConfigurationRepository) {
    companion object {
        const val REMOTE_CONFIG_WORKER = "RemoteConfigWorker"
    }

    fun scheduleWorker(context: Context) {
        CoroutineScope(Dispatchers.Default).launch {

            var configurationUpdateInterval = 15L

            val job = launch(Dispatchers.IO) {
                val config: SchedulerConfiguration? =
                    schedulerConfigurationRepository.getLastScheduler()

                config?.let {
                    configurationUpdateInterval = it.configurationUpdateInterval
                }
            }

            job.join()

            val remoteConfigWorkRequest = PeriodicWorkRequestBuilder<RemoteConfigWorker>(
                configurationUpdateInterval, TimeUnit.MINUTES
            )
                .build()

            WorkManager
                .getInstance(context)
                .enqueueUniquePeriodicWork(
                    REMOTE_CONFIG_WORKER,
                    ExistingPeriodicWorkPolicy.REPLACE,
                    remoteConfigWorkRequest
                )
        }
    }
}