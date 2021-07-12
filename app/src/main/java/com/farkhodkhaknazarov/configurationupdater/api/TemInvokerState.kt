package com.farkhodkhaknazarov.configurationupdater.api

import com.farkhodkhaknazarov.configurationupdater.core.model.TerminalConfiguration


sealed class TemInvokerState(var description: String) {
    class Unknown(description: String = "Service state unknown"): TemInvokerState(description)
    class Invoking(description: String = "Invoking configuration", val invoking: Boolean, val configuration: TerminalConfiguration, val invokeResult: TemInvokerResultEnum): TemInvokerState(description)
    class Ready(description: String = "Service is ready"): TemInvokerState(description)
    class ConfigurationUpdateResult(description: String = ""): TemInvokerState(description)
    class TerminalConfigurationRead(description: String = "", val configurationList: List<TerminalConfiguration>): TemInvokerState(description)
}