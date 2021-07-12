package com.farkhodkhaknazarov.configurationupdater.feature.persistence.room

import com.farkhodkhaknazarov.configurationupdater.core.model.SchedulerConfiguration
import com.farkhodkhaknazarov.configurationupdater.feature.persistence.api.SchedulerConfigurationRepository
import com.farkhodkhaknazarov.configurationupdater.feature.persistence.room.model.toSchedulerConfiguration
import com.farkhodkhaknazarov.configurationupdater.feature.persistence.room.model.toSchedulerConfigurationModule


class SchedulerConfigurationRepositoryImpl(
    private val db: ConfigurationDataBase
) : SchedulerConfigurationRepository {

    override suspend fun deleteAllConfigurationScheduler() {
        db.schedulerConfigurationDao().also { confDao ->
            confDao.deleteAll()
        }
    }

    override suspend fun insert(configuration: SchedulerConfiguration) {
        db.schedulerConfigurationDao().also { confDao ->
            confDao.insert(configuration.toSchedulerConfigurationModule())
        }
    }

    override suspend fun updateConfigurationInvocationInterval(configuration: SchedulerConfiguration) {
        db.schedulerConfigurationDao().getAllLines().also {
            if (it.isEmpty()) {
                db.schedulerConfigurationDao().insert(configuration.toSchedulerConfigurationModule())
            }
        }

        db.schedulerConfigurationDao().updateConfigurationInvocationInterval(configuration.invocationInterval)
    }

    override suspend fun updateConfigurationUpdateInterval(configuration: SchedulerConfiguration) {
        db.schedulerConfigurationDao().getAllLines().also {
            if (it.isEmpty()) {
                db.schedulerConfigurationDao().insert(configuration.toSchedulerConfigurationModule())
            }
        }

        db.schedulerConfigurationDao().updateConfigurationUpdateInterval(configuration.configurationUpdateInterval)
    }

    override suspend fun getLastScheduler(): SchedulerConfiguration? {
        val scheduler = db.schedulerConfigurationDao()
            .getLastScheduler()

        scheduler?.let {
            return scheduler.toSchedulerConfiguration()
        }
        return null
    }
}