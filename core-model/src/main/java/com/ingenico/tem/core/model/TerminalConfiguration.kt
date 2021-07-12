package com.farkhodkhaknazarov.configurationupdater.core.model

data class TerminalConfiguration(
    var id: Long,
    var packageName: String,
    var serialNumber: String = "",
    var serverUrl: String = "",
    var mutualTls: Boolean = false,
    var keyOption: Int = 0,
    var clientKey: String = "",
    var clientKeyPassword: String = "",
    var trustCa: String = "",
    var trustCaPassword: String = ""
)


