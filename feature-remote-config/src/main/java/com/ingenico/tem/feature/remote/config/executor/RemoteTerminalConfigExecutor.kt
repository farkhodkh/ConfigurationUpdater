package com.farkhodkhaknazarov.configurationupdater.feature.remote.config.executor

import com.farkhodkhaknazarov.configurationupdater.core.model.Configuration
import com.farkhodkhaknazarov.configurationupdater.core.model.SchedulerConfiguration
import com.farkhodkhaknazarov.configurationupdater.core.model.TerminalConfiguration
import com.farkhodkhaknazarov.configurationupdater.feature.remote.config.model.ConfigurationAction
import com.farkhodkhaknazarov.configurationupdater.feature.remote.config.model.ConfigurationModel

interface RemoteTerminalConfigExecutor {
    fun execute(configuration: ConfigurationModel)
    suspend fun saveConfigurationPassword(configurationAction: ConfigurationAction)
    suspend fun deleteAllConfigurationScheduler()
    suspend fun insertConfigurationScheduler(configurationAction: ConfigurationAction)
    suspend fun saveConfigurationUpdateInterval(configurationAction: ConfigurationAction)
    suspend fun saveConfigurationInvocationInterval(configurationAction: ConfigurationAction)
    suspend fun getLastScheduler(): SchedulerConfiguration?

    suspend fun saveConfigurations(configurationAction: ConfigurationAction)
    suspend fun getConfiguration(): Configuration?
    suspend fun getAllConfigurations(): List<TerminalConfiguration>
    suspend fun deleteAllConfigurations(configurationAction: ConfigurationAction)
    suspend fun deleteConfigurations(configurationAction: ConfigurationAction)
}