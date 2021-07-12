package com.farkhodkhaknazarov.configurationupdater.feature.persistence.api

import com.farkhodkhaknazarov.configurationupdater.core.model.SchedulerConfiguration


interface SchedulerConfigurationRepository {
    /**
     * Method deletes all data from [SchedulerConfiguration] entity.
     */
    suspend fun deleteAllConfigurationScheduler()

    /**
     * Inserts new [SchedulerConfiguration] entity.
     *
     * @param key [SchedulerConfiguration] object to insert.
     */
    suspend fun insert(configuration: SchedulerConfiguration)

    /**
     * Updates InvocationInterval for all lines for [SchedulerConfiguration] entity.
     *
     * @param key [SchedulerConfiguration] object to insert.
     */
    suspend fun updateConfigurationInvocationInterval(configuration: SchedulerConfiguration)

    /**
     * Updates ConfigurationUpdateInterval for all lines for [SchedulerConfiguration] entity.
     *
     * @param key [SchedulerConfiguration] object to insert.
     */
    suspend fun updateConfigurationUpdateInterval(configuration: SchedulerConfiguration)

    /**
     * Method gets the last line of[SchedulerConfiguration] entity.
     */
    suspend fun getLastScheduler(): SchedulerConfiguration?
}
