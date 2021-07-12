package com.farkhodkhaknazarov.configurationupdater.feature.persistence.api

interface TerminalConfigurationPersistenceApi {
    /**
     * @return Instantiated [TerminalConfigurationRepository] object.
     */
    fun terminalConfigurationRepository(): TerminalConfigurationRepository

    /**
     * @return Instantiated [SchedulerConfigurationRepository] object.
     */
    fun schedulerConfigurationRepository(): SchedulerConfigurationRepository
}