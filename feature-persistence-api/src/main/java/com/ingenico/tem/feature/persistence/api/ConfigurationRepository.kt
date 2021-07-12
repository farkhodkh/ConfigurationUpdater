package com.farkhodkhaknazarov.configurationupdater.feature.persistence.api

import com.farkhodkhaknazarov.configurationupdater.core.model.Configuration


interface ConfigurationRepository {
    /**
     * Method deletes all data from [Configuration] entity.
     */
    suspend fun deleteAllConfigurations()

    /**
     * Inserts new [Configuration] entity.
     *
     * @param key [Configuration] object to insert.
     */
    suspend fun insert(configuration: Configuration)

    suspend fun getLastConfiguration(): Configuration?
}