package com.farkhodkhaknazarov.configurationupdater.feature.remote.config.model

import com.farkhodkhaknazarov.configurationupdater.core.model.Configuration
import com.farkhodkhaknazarov.configurationupdater.core.model.SchedulerConfiguration
import com.farkhodkhaknazarov.configurationupdater.core.model.TerminalConfiguration

sealed class ConfigurationAction {
    class Default(): ConfigurationAction()
    class ConfigurationPassword(val configuration: Configuration): ConfigurationAction()
    class ConfigurationUpdateInterval(val schedulerConfiguration: SchedulerConfiguration): ConfigurationAction()
    class ConfigurationInvocationInterval(val schedulerConfiguration: SchedulerConfiguration): ConfigurationAction()
    class AddConfigurations(val list: List<TerminalConfiguration> = emptyList()): ConfigurationAction()
    class DeleteAllConfigurations(val deleteAll: Boolean): ConfigurationAction()
    class DeleteConfigurations(val list:List<TerminalConfiguration> = emptyList()): ConfigurationAction()
}