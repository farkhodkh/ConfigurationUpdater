package com.farkhodkhaknazarov.configurationupdater.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farkhodkhaknazarov.configurationupdater.core.model.SchedulerConfiguration
import com.farkhodkhaknazarov.configurationupdater.feature.remote.config.executor.RemoteTerminalConfigExecutor
import com.farkhodkhaknazarov.configurationupdater.feature.remote.config.model.ConfigurationAction
import com.farkhodkhaknazarov.configurationupdater.feature.remote.config.model.ConfigurationModel
import com.farkhodkhaknazarov.configurationupdater.feature.remote.config.util.ParametersParser
import com.farkhodkhaknazarov.configurationupdater.ui.state.ConfigurationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.security.MessageDigest
import javax.inject.Inject

@HiltViewModel
class ConfigurationFragmentViewModel @Inject constructor(
    val packageName: String,
    var remoteTerminalConfigExecutor: RemoteTerminalConfigExecutor
) : ViewModel() {
    private val _state = MutableStateFlow<ConfigurationState>(ConfigurationState.Unknown())
    val state: StateFlow<ConfigurationState> = _state

    private val _schedule = MutableStateFlow(SchedulerConfiguration(0, 0, 0))
    val schedule: StateFlow<SchedulerConfiguration> = _schedule

    fun importConfigurationFile(file: File) {
        viewModelScope.launch(Dispatchers.IO) {
            val configuration: ConfigurationModel =
                file.inputStream()
                    .use { params ->
                        ParametersParser(packageName).parse(
                            params
                        )
                    }

            remoteTerminalConfigExecutor.execute(configuration)
            _state.tryEmit(
                ConfigurationState.ConfigurationUpdated(
                    getConfigurationDescription(
                        configuration
                    )
                )
            )
        }
    }

    private fun getConfigurationDescription(configuration: ConfigurationModel): String {
        var resultText = "Upload result\n"

        configuration.actions.forEach { action ->
            when (action) {
                is ConfigurationAction.ConfigurationPassword -> {
                    resultText += "Configuration access password updated\n"
                }
                is ConfigurationAction.ConfigurationInvocationInterval -> {
                    resultText += "Configuration invocation interval set to ${action.schedulerConfiguration.invocationInterval} min.\n"
                }
                is ConfigurationAction.ConfigurationUpdateInterval -> {
                    resultText += "Configuration update interval set to ${action.schedulerConfiguration.configurationUpdateInterval} min.\n"
                }
                is ConfigurationAction.DeleteAllConfigurations -> {
                    if (action.deleteAll) {
                        resultText += "Deleted all stored configurations\n"
                    }
                }
                is ConfigurationAction.DeleteConfigurations -> {
                    action.list.forEach { configuration ->
                        resultText += "Deleted configuration for server ${configuration.serverUrl}\n"
                    }
                }
                is ConfigurationAction.AddConfigurations -> {
                    action.list.forEach { configuration ->
                        resultText += "Added configuration for server ${configuration.serverUrl}\n"
                    }
                }
                else -> {
                    throw IllegalStateException("Illegal configuration action")
                }
            }
        }
        return resultText
    }

    fun requireConfigurationPassword(enteredPass: String = "") {
        _state.tryEmit(ConfigurationState.Unknown())
        viewModelScope.launch(Dispatchers.IO) {
            remoteTerminalConfigExecutor
                .getConfiguration()?.also { configuration ->
                    if (configuration.configurationPassword.isNotEmpty()) {
                        when {
                            configuration.configurationPassword.isNotEmpty() -> {
                                if (!comparePassword(enteredPass, configuration.configurationPassword)) {
                                    _state.tryEmit(ConfigurationState.RequirePassword())
                                }
                            }
                            enteredPass.isEmpty() -> {
                                _state.tryEmit(ConfigurationState.RequirePassword())
                            }
                            else -> {
                                _state.tryEmit(ConfigurationState.Unknown())
                            }
                        }
                    }
                }
        }
    }

    private fun comparePassword(enteredPass: String, storedHash: String): Boolean {

        if (enteredPass.isEmpty()) return false

        // Create MD5 Hash
        val digest = MessageDigest
            .getInstance("MD5")

        digest.update(enteredPass.toByteArray(Charsets.UTF_8))
        val messageDigest = digest.digest()

        // Create Hex String
        val hexString = StringBuilder()
        for (aMessageDigest in messageDigest) {
            var h = Integer.toHexString(0xFF and aMessageDigest.toInt())
            while (h.length < 2) h = "0$h"
            hexString.append(h)
        }
        return hexString.toString().equals(storedHash)
    }

    fun saveConfiguration(confUpdateIntervalStr: String, invocationIntervalStr: String) {
        val confUpdateInterval: Long
        val invocationInterval: Long

        _state.tryEmit(ConfigurationState.Unknown())

        try {
            confUpdateInterval = confUpdateIntervalStr.toLong()
            invocationInterval = invocationIntervalStr.toLong()
        } catch (ex: Exception) {
            _state.tryEmit(ConfigurationState.ConfigurationSaveError("Incorrect intervals, ${ex.localizedMessage}"))
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            val action = ConfigurationAction.ConfigurationUpdateInterval(
                SchedulerConfiguration(
                    0,
                    confUpdateInterval,
                    invocationInterval
                )
            )
            try {
                remoteTerminalConfigExecutor.insertConfigurationScheduler(action)
                _state.tryEmit(ConfigurationState.ConfigurationUpdated("Scheduler saved"))
            } catch (ex: Exception) {
                _state.tryEmit(ConfigurationState.ConfigurationSaveError("Scheduler save error, ${ex.localizedMessage}"))
            }
        }
    }

    fun getCurrentSchedule() {
        viewModelScope.launch {
            remoteTerminalConfigExecutor
                .getLastScheduler()?.also {
                    _schedule.tryEmit(it)
                }
        }
    }
}