package com.farkhodkhaknazarov.configurationupdater.api

import com.farkhodkhaknazarov.configurationupdater.core.model.TerminalConfiguration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import org.slf4j.LoggerFactory

interface TemInvokerExecutor {
    fun getServiceAsync(): Deferred<String>
    fun execute(configuration: TerminalConfiguration)
    fun executeAsync(configuration: TerminalConfiguration): Flow<TemInvokerResultEnum>
    fun closeService()
}

class TemInvokerExecutorImpl : TemInvokerExecutor {
    private val logger by lazy { LoggerFactory.getLogger(this::class.java) }
    val scope = CoroutineScope(Dispatchers.IO)

    override fun getServiceAsync(): Deferred<String> = scope.async {
        logger.debug("Not implemented method")
        "Not implemented method"
    }

    override fun execute(configuration: TerminalConfiguration) {
        logger.debug("Not implemented method")
    }

    override fun executeAsync(configuration: TerminalConfiguration): Flow<TemInvokerResultEnum> =
        callbackFlow {
            logger.debug("Not implemented method")
            offer(TemInvokerResultEnum.SUCCESS)
        }

    override fun closeService() {
        logger.debug("Not implemented method")
    }
}