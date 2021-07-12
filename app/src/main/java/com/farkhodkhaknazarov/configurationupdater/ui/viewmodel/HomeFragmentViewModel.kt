package com.farkhodkhaknazarov.configurationupdater.ui.viewmodel

import com.farkhodkhaknazarov.configurationupdater.feature.remote.config.executor.RemoteTerminalConfigExecutor


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farkhodkhaknazarov.configurationupdater.api.TemInvokerExecutor
import com.farkhodkhaknazarov.configurationupdater.api.TemInvokerResultEnum
import com.farkhodkhaknazarov.configurationupdater.api.TemInvokerState
import com.farkhodkhaknazarov.configurationupdater.core.model.TerminalConfiguration
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    private var temInvokerExecutor: TemInvokerExecutor,
    var remoteTerminalConfigExecutor: RemoteTerminalConfigExecutor
) : ViewModel() {

    val logger = LoggerFactory.getLogger(this::class.java.canonicalName)
    private val _state = MutableStateFlow<TemInvokerState>(TemInvokerState.Unknown())
    val state: StateFlow<TemInvokerState> = _state

    fun checkServiceState() {
        viewModelScope.launch {
            temInvokerExecutor
                .getServiceAsync()
                .await()
                .let {
                    _state.tryEmit(TemInvokerState.Ready())
                }
        }
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            remoteTerminalConfigExecutor
                .getAllConfigurations()
                .also {
                    _state.value = TemInvokerState.TerminalConfigurationRead(configurationList = it)
                }
        }
    }

    suspend fun invokeConfiguration(configuration: TerminalConfiguration) {
        viewModelScope.launch(Dispatchers.Main) {
            _state.value = TemInvokerState.Invoking(
                "Invoking configuration ${configuration.serverUrl}",
                true,
                configuration,
                TemInvokerResultEnum.UNKNOWN
            )
        }.join()

        withContext(Dispatchers.Default) {
            temInvokerExecutor
                .executeAsync(configuration)
                .catch { error ->
                    logger.error("TemInvoker request error: $error")
                    error.message.let { message ->
                        _state.value = TemInvokerState.Invoking(
                            "Invoking for configuration ${configuration.serverUrl} has been failed! Details: $message ${error.cause?.localizedMessage ?: ""}",
                            false,
                            configuration,
                            TemInvokerResultEnum.ERROR
                        )
                    }
                }
                .onEach { result ->
                    logger.info("TemInvoker work success. Result: ${result}")

                    when (result) {
                        TemInvokerResultEnum.COMPLETE -> {
                            _state.value =
                                TemInvokerState.ConfigurationUpdateResult("Configuration invoke for ${configuration.serverUrl} has been finished! No Update")
                        }
                        else -> {
                            _state.value =
                                TemInvokerState.ConfigurationUpdateResult("Configuration invoke for ${configuration.serverUrl} has been finished SUCCESSFULLY!")
                        }
                    }
                    _state.value = TemInvokerState.Invoking(
                        "Invoking for configuration ${configuration.serverUrl} finished",
                        false,
                        configuration,
                        result
                    )
                }.launchIn(this)
        }
    }
}
