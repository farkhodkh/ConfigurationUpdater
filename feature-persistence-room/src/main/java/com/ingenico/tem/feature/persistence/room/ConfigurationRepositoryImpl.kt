package com.farkhodkhaknazarov.configurationupdater.feature.persistence.room

import com.farkhodkhaknazarov.configurationupdater.core.model.Configuration
import com.farkhodkhaknazarov.configurationupdater.feature.persistence.api.ConfigurationRepository
import com.farkhodkhaknazarov.configurationupdater.feature.persistence.room.model.toConfiguration
import com.farkhodkhaknazarov.configurationupdater.feature.persistence.room.model.toConfigurationModel


class ConfigurationRepositoryImpl(private val db: ConfigurationDataBase): ConfigurationRepository {
    override suspend fun deleteAllConfigurations() {
        db.configurationDao().deleteAll()
    }

    override suspend fun insert(configuration: Configuration) {
        db.configurationDao().insert(configuration.toConfigurationModel())
    }

    override suspend fun getLastConfiguration(): Configuration? =
        db.configurationDao().getLastConfiguration()?.toConfiguration()
}