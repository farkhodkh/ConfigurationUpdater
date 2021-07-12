package com.farkhodkhaknazarov.configurationupdater.feature.persistence.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.farkhodkhaknazarov.configurationupdater.core.model.SchedulerConfiguration

@Entity(tableName = SchedulerConfigurationModule.Database.TABLE_NAME)
data class SchedulerConfigurationModule(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Database.COL_ID)
    var id: Long,
    @ColumnInfo(name = Database.COL_CONFIGURATION_UPDATE_INTERVAL)
    var configurationUpdateInterval: Long,
    @ColumnInfo(name = Database.COL_INVOCATION_INTERVAL)
    var invocationInterval: Long
) {
    object Database {
        const val TABLE_NAME = "scheduler_configuration"

        const val COL_ID = "id"
        const val COL_CONFIGURATION_UPDATE_INTERVAL = "configuration_update_interval"
        const val COL_INVOCATION_INTERVAL = "invocation_interval"
    }
}

fun SchedulerConfigurationModule.toSchedulerConfiguration(): SchedulerConfiguration =
    SchedulerConfiguration(
        0,
        this.configurationUpdateInterval,
        this.invocationInterval
    )

fun SchedulerConfiguration.toSchedulerConfigurationModule(): SchedulerConfigurationModule =
    SchedulerConfigurationModule(
        0,
        this.configurationUpdateInterval,
        this.invocationInterval
    )