package com.farkhodkhaknazarov.configurationupdater.core.model

data class SchedulerConfiguration(
    var id: Long,
    var configurationUpdateInterval: Long = 0,
    var invocationInterval: Long = 0
)