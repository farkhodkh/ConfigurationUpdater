package com.farkhodkhaknazarov.configurationupdater.feature.persistence.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.farkhodkhaknazarov.configurationupdater.feature.persistence.room.dao.ConfigurationDao
import com.farkhodkhaknazarov.configurationupdater.feature.persistence.room.dao.SchedulerConfigurationDao
import com.farkhodkhaknazarov.configurationupdater.feature.persistence.room.model.ConfigurationModule
import com.farkhodkhaknazarov.configurationupdater.feature.persistence.room.model.SchedulerConfigurationModule
import com.farkhodkhaknazarov.configurationupdater.feature.persistence.room.model.TerminalConfigurationModule
import com.farkhodkhaknazarov.configurationupdater.feature.persistence.room.dao.TerminalConfigurationDao

@Database(
    entities = [
        TerminalConfigurationModule::class,
        SchedulerConfigurationModule::class,
        ConfigurationModule::class
    ],
    version = 1
)
@TypeConverters(Converters::class)
abstract class ConfigurationDataBase : RoomDatabase() {
    internal abstract fun configurationDao(): ConfigurationDao
    internal abstract fun terminalConfigurationDao(): TerminalConfigurationDao
    internal abstract fun schedulerConfigurationDao(): SchedulerConfigurationDao

    companion object {
        const val DATABASE_NAME = "configuration_db"
    }
}