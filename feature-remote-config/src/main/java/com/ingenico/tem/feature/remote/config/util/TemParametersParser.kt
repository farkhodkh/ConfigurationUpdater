package com.farkhodkhaknazarov.configurationupdater.feature.remote.config.util

import java.io.InputStream

interface TemParametersParser<T> {
    suspend fun parse(inStream: InputStream): T
}