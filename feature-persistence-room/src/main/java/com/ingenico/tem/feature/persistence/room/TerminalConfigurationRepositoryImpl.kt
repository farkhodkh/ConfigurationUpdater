package com.farkhodkhaknazarov.configurationupdater.feature.persistence.room

import com.farkhodkhaknazarov.configurationupdater.core.model.TerminalConfiguration
import com.farkhodkhaknazarov.configurationupdater.feature.persistence.api.TerminalConfigurationRepository
import com.farkhodkhaknazarov.configurationupdater.feature.persistence.room.model.toTerminalConfiguration
import com.farkhodkhaknazarov.configurationupdater.feature.persistence.room.model.toTerminalConfigurationModule

class TerminalConfigurationRepositoryImpl(
    private val db: ConfigurationDataBase
): TerminalConfigurationRepository {

    override suspend fun insert(configuration: TerminalConfiguration) {
        db.terminalConfigurationDao().insert(configuration.toTerminalConfigurationModule())
    }

    override suspend fun update(configuration: TerminalConfiguration) {
        db.terminalConfigurationDao().update(configuration.toTerminalConfigurationModule())
    }

    override suspend fun delete(configuration: TerminalConfiguration) {
        db.terminalConfigurationDao().delete(configuration.toTerminalConfigurationModule())
    }

    override suspend fun delete(serialNumber: String) {
        db.terminalConfigurationDao().deleteBySerialNumber(serialNumber)
    }

    override suspend fun deleteAll() {
        db.terminalConfigurationDao().deleteAll()
    }

    override suspend fun getAll(): List<TerminalConfiguration> =
        db.terminalConfigurationDao().getAll().map {
            it.toTerminalConfiguration()
        }
}