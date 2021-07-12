package com.farkhodkhaknazarov.configurationupdater.feature.remote.config.util

import com.farkhodkhaknazarov.configurationupdater.core.model.Configuration
import com.farkhodkhaknazarov.configurationupdater.core.model.SchedulerConfiguration
import com.farkhodkhaknazarov.configurationupdater.core.model.TerminalConfiguration
import com.farkhodkhaknazarov.configurationupdater.feature.remote.config.enums.UpdateIntervalEnum
import com.farkhodkhaknazarov.configurationupdater.feature.remote.config.model.ConfigurationAction
import com.farkhodkhaknazarov.configurationupdater.feature.remote.config.model.ConfigurationModel
import org.slf4j.LoggerFactory
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStream

class ParametersParser(private val packageName: String) : TemParametersParser<ConfigurationModel> {

    private val logger by lazy(LazyThreadSafetyMode.NONE) {
        LoggerFactory.getLogger(this::class.java)
    }

    companion object {
        private const val TAG_CONFIGURATION = "Configurations"
        private const val TAG_SCHEDULER_PASSWORD = "configurationPassword"
        private const val TAG_CONFIGURATION_UPDATE_INTERVAL = "configurationUpdateInterval"
        private const val TAG_INVOCATION_INTERVAL = "configurationInvocationInterval"
        private const val TAG_DELETE_ALL_CONFIGURATIONS = "deleteAllConfigurations"
        private const val TAG_DELETE_CONFIGURATIONS = "deleteConfigurations"
        private const val TAG_AVAILABLE_CONFIGURATIONS = "availableConfigurations"
        private const val TAG_SERIAL_NUMBER = "serialNumber"
        private const val TAG_SERVER_URL = "serverUrl"
        private const val TAG_MUTUAL_TLS = "mutualTls"
        private const val TAG_KEY_OPTION = "keyOption"
        private const val TAG_CLIENT_KEY = "clientKey"
        private const val TAG_CLIENT_KEY_PASSWORD = "clientKeyPassword"
        private const val TAG_TRUST_CA = "trustCa"
        private const val TAG_TRUST_CA_PASSWORD = "trustCaPassword"

    }

    override suspend fun parse(inStream: InputStream): ConfigurationModel {
        val parser = getParser(inStream)
        return parser.parseParameters()
    }

    private fun getParser(inStream: InputStream): XmlPullParser {
        return XmlPullParserFactory.newInstance().newPullParser().apply {
            setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
            setInput(inStream, null)
        }
    }

    private fun XmlPullParser.parseParameters(): ConfigurationModel {
        next()
        require(XmlPullParser.START_TAG, null, TAG_CONFIGURATION)

        val listOfActions = listOf(
            TAG_SCHEDULER_PASSWORD,
            TAG_CONFIGURATION_UPDATE_INTERVAL,
            TAG_INVOCATION_INTERVAL,
            TAG_DELETE_ALL_CONFIGURATIONS,
            TAG_DELETE_CONFIGURATIONS,
            TAG_AVAILABLE_CONFIGURATIONS
        )

        var actions: Collection<ConfigurationAction> = emptyList()

        while (next() != XmlPullParser.END_TAG) {
            if (eventType != XmlPullParser.START_TAG) continue

            when (name) {
                in listOfActions -> actions += parseActions()
                else -> {
                    logger.debug(
                        "[parseKMSParameters] Skipping unknown tag inside tag `{}`. Tag=[]",
                        TAG_CONFIGURATION,
                        name
                    )
                }
            }
        }

        return ConfigurationModel(
            ConfigurationModel.VERSION_1,
            actions
        )
    }

    private fun XmlPullParser.parseActions(): ConfigurationAction {
        var action: ConfigurationAction = ConfigurationAction.Default()

        while (eventType != XmlPullParser.END_TAG) {
            if (eventType != XmlPullParser.START_TAG) continue
            when (name) {
                TAG_SCHEDULER_PASSWORD -> {
                    action = parseSchedulerPassword()
                }
                TAG_CONFIGURATION_UPDATE_INTERVAL -> {
                    action = parseConfigurationUpdateInterval()
                }
                TAG_INVOCATION_INTERVAL -> {
                    action = parseInvocationInterval()
                }
                TAG_DELETE_ALL_CONFIGURATIONS -> {
                    action = parseDeleteAllConfigurations()
                }
                TAG_DELETE_CONFIGURATIONS -> {
                    action = parseDeleteConfigurations()
                }
                TAG_AVAILABLE_CONFIGURATIONS -> {
                    action = parseAvailableConfigurations()
                }
            }
        }
        return action
    }

    private fun XmlPullParser.parseSchedulerPassword() : ConfigurationAction.ConfigurationPassword {
        var passHash = ""

        while (next() != XmlPullParser.END_TAG) {
            passHash = text
        }

        return ConfigurationAction.ConfigurationPassword(Configuration(0, passHash))
    }

    private fun XmlPullParser.parseConfigurationUpdateInterval(): ConfigurationAction.ConfigurationUpdateInterval {
        var updateInterval = UpdateIntervalEnum.MIN_15
        while (next() != XmlPullParser.END_TAG) {
            UpdateIntervalEnum.getById(text)?.let { updateValue ->
                updateInterval = updateValue
            }
        }
        return ConfigurationAction.ConfigurationUpdateInterval(
            SchedulerConfiguration(
                0,
                updateInterval.minutes
            )
        )
    }

    private fun XmlPullParser.parseInvocationInterval(): ConfigurationAction.ConfigurationInvocationInterval {
        var invocationInterval = UpdateIntervalEnum.MIN_15
        while (next() != XmlPullParser.END_TAG) {
            UpdateIntervalEnum.getById(text)?.let { updateValue ->
                invocationInterval = updateValue
            }
        }

        return ConfigurationAction.ConfigurationInvocationInterval(
            SchedulerConfiguration(
                0,
                0,
                invocationInterval.minutes
            )
        )
    }

    private fun XmlPullParser.parseDeleteAllConfigurations(): ConfigurationAction.DeleteAllConfigurations {
        var deleteAll = false
        while (next() != XmlPullParser.END_TAG) {
            deleteAll = text.equals("1")
        }
        return ConfigurationAction.DeleteAllConfigurations(deleteAll)
    }

    private fun XmlPullParser.parseDeleteConfigurations(): ConfigurationAction.DeleteConfigurations {
        val list = mutableListOf<TerminalConfiguration>()
        val entity: TerminalConfiguration?
        var serialNumber = ""

        while (next() != XmlPullParser.END_TAG) {
            when (name) {
                TAG_SERIAL_NUMBER -> serialNumber = nextText()
            }
        }

        entity = TerminalConfiguration(0, packageName, serialNumber = serialNumber)

        list.add(entity)

        return ConfigurationAction.DeleteConfigurations(list)
    }

    private fun XmlPullParser.parseAvailableConfigurations(): ConfigurationAction.AddConfigurations {
        val list = mutableListOf<TerminalConfiguration>()
        val entity: TerminalConfiguration?
        var serialNumber = ""
        var serverUrl = ""
        var mutualTls = false
        var keyOption = 0
        var clientKey = ""
        var clientKeyPassword = ""
        var trustCa = ""
        var trustCaPassword = ""

        while (next() != XmlPullParser.END_TAG) {
            when (name) {
                TAG_SERIAL_NUMBER -> serialNumber = nextText()
                TAG_SERVER_URL -> serverUrl = nextText()
                TAG_MUTUAL_TLS -> mutualTls = nextText().equals("1")
                TAG_KEY_OPTION -> keyOption = nextText().toInt()
                TAG_CLIENT_KEY -> clientKey = nextText()
                TAG_CLIENT_KEY_PASSWORD -> clientKeyPassword = nextText()
                TAG_TRUST_CA -> trustCa = nextText()
                TAG_TRUST_CA_PASSWORD -> trustCaPassword = nextText()
            }
        }

        entity = TerminalConfiguration(
            0,
            packageName,
            serialNumber = serialNumber,
            serverUrl = serverUrl,
            mutualTls = mutualTls,
            keyOption = keyOption,
            clientKey = clientKey,
            clientKeyPassword = clientKeyPassword,
            trustCa = trustCa,
            trustCaPassword = trustCaPassword
        )

        list.add(entity)

        return ConfigurationAction.AddConfigurations(list)
    }
}