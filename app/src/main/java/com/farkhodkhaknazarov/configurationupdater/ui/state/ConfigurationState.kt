package com.farkhodkhaknazarov.configurationupdater.ui.state

sealed class ConfigurationState(val description: String) {
    class Unknown(description: String = "Configuration state unknown"): ConfigurationState(description)
    class ConfigurationSaveError(description: String): ConfigurationState(description)
    class RequirePassword(description: String = "Configuration need access password"): ConfigurationState(description)
    class ConfigurationUpdated(description: String): ConfigurationState(description)
}