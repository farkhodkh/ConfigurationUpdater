package com.farkhodkhaknazarov.configurationupdater.feature.remote.config.model

class ConfigurationModel(val version: String, val actions: Collection<ConfigurationAction>) {

    companion object Versions {
        val VERSION_1 = "0001"
    }
}