package com.farkhodkhaknazarov.configurationupdater.feature.remote.config.executor

import com.farkhodkhaknazarov.configurationupdater.core.model.Configuration
import com.farkhodkhaknazarov.configurationupdater.core.model.SchedulerConfiguration
import com.farkhodkhaknazarov.configurationupdater.core.model.TerminalConfiguration
import com.farkhodkhaknazarov.configurationupdater.feature.persistence.api.ConfigurationRepository
import com.farkhodkhaknazarov.configurationupdater.feature.persistence.api.SchedulerConfigurationRepository
import com.farkhodkhaknazarov.configurationupdater.feature.persistence.api.TerminalConfigurationRepository
import com.farkhodkhaknazarov.configurationupdater.feature.remote.config.model.ConfigurationAction
import com.farkhodkhaknazarov.configurationupdater.feature.remote.config.model.ConfigurationModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.slf4j.LoggerFactory

class RemoteTerminalConfigExecutorImpl(
    private val configurationRepository: ConfigurationRepository,
    private val terminalConfigurationRepository: TerminalConfigurationRepository,
    private val schedulerSchedulerConfigurationRepository: SchedulerConfigurationRepository
) :
    RemoteTerminalConfigExecutor {

    private val logger by lazy { LoggerFactory.getLogger(this::class.java) }

    override fun execute(configuration: ConfigurationModel) {
        GlobalScope.launch(Dispatchers.IO) {
            configuration.actions.forEach {
                when (it) {
                    is ConfigurationAction.Default -> {
                        //Not used
                    }
                    is ConfigurationAction.ConfigurationPassword -> {
                        saveConfigurationPassword(it)
                    }
                    is ConfigurationAction.ConfigurationUpdateInterval -> {
                        saveConfigurationUpdateInterval(it)
                    }
                    is ConfigurationAction.ConfigurationInvocationInterval -> {
                        saveConfigurationInvocationInterval(it)
                    }
                    is ConfigurationAction.AddConfigurations -> {
                        saveConfigurations(it)
                    }
                    is ConfigurationAction.DeleteConfigurations -> {
                        deleteConfigurations(it)
                    }
                    is ConfigurationAction.DeleteAllConfigurations -> {
                        deleteAllConfigurations(it)
                    }
                }
            }
        }
    }

    override suspend fun saveConfigurationPassword(configurationAction: ConfigurationAction) {
        if (configurationAction is ConfigurationAction.ConfigurationPassword) {
            configurationRepository.insert(configurationAction.configuration)
        }
    }

    override suspend fun getConfiguration(): Configuration? {
        return configurationRepository.getLastConfiguration()
    }

    override suspend fun deleteAllConfigurationScheduler() {
        schedulerSchedulerConfigurationRepository.deleteAllConfigurationScheduler()
    }

    override suspend fun insertConfigurationScheduler(configurationAction: ConfigurationAction) {
        if (configurationAction is ConfigurationAction.ConfigurationUpdateInterval) {
            schedulerSchedulerConfigurationRepository.insert(configurationAction.schedulerConfiguration)
        }
    }

    override suspend fun saveConfigurationUpdateInterval(configurationAction: ConfigurationAction) {
        if (configurationAction is ConfigurationAction.ConfigurationUpdateInterval) {
            schedulerSchedulerConfigurationRepository.updateConfigurationUpdateInterval(configurationAction.schedulerConfiguration)
        }
    }

    override suspend fun saveConfigurationInvocationInterval(configurationAction: ConfigurationAction) {
        if (configurationAction is ConfigurationAction.ConfigurationInvocationInterval) {
            schedulerSchedulerConfigurationRepository.updateConfigurationInvocationInterval(
                configurationAction.schedulerConfiguration
            )
        }
    }

    override suspend fun getLastScheduler(): SchedulerConfiguration? =
        schedulerSchedulerConfigurationRepository.getLastScheduler()

    override suspend fun saveConfigurations(configurationAction: ConfigurationAction) {
        (configurationAction as ConfigurationAction.AddConfigurations).list.forEach { configuration ->
            terminalConfigurationRepository.insert(configuration)
        }
    }

    override suspend fun deleteAllConfigurations(configurationAction: ConfigurationAction) {
        if ((configurationAction as ConfigurationAction.DeleteAllConfigurations).deleteAll) {
            terminalConfigurationRepository.deleteAll()
        }
    }

    override suspend fun deleteConfigurations(configurationAction: ConfigurationAction) {
        (configurationAction as ConfigurationAction.DeleteConfigurations).list.forEach { configuration ->
            terminalConfigurationRepository.delete(configuration)
        }
    }

    override suspend fun getAllConfigurations(): List<TerminalConfiguration> =
        terminalConfigurationRepository.getAll()
}