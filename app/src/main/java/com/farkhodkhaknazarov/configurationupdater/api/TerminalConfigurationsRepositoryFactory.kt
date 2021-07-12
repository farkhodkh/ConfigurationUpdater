package com.farkhodkhaknazarov.configurationupdater.api

import android.content.Context
import androidx.room.Room
import com.farkhodkhaknazarov.configurationupdater.feature.persistence.api.ConfigurationRepository
import com.farkhodkhaknazarov.configurationupdater.feature.persistence.api.SchedulerConfigurationRepository
import com.farkhodkhaknazarov.configurationupdater.feature.persistence.api.TerminalConfigurationRepository
import com.farkhodkhaknazarov.configurationupdater.feature.persistence.room.ConfigurationDataBase
import com.farkhodkhaknazarov.configurationupdater.feature.persistence.room.ConfigurationRepositoryImpl
import com.farkhodkhaknazarov.configurationupdater.feature.persistence.room.SchedulerConfigurationRepositoryImpl
import com.farkhodkhaknazarov.configurationupdater.feature.persistence.room.TerminalConfigurationRepositoryImpl

class TerminalConfigurationsRepositoryFactory(private val context: Context) {
    private val db: ConfigurationDataBase by lazy {
        Room.databaseBuilder(
            context.applicationContext,
            ConfigurationDataBase::class.java,
            ConfigurationDataBase.DATABASE_NAME
        )
            .build()
    }

    fun createConfigurationRepository(): ConfigurationRepository =
        ConfigurationRepositoryImpl(db)

    fun createTerminalConfigurationRepository(): TerminalConfigurationRepository =
        TerminalConfigurationRepositoryImpl(db)

    fun createSchedulerConfigurationRepository(): SchedulerConfigurationRepository =
        SchedulerConfigurationRepositoryImpl(db)
}