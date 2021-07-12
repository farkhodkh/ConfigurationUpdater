package com.farkhodkhaknazarov.configurationupdater.feature.persistence.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.farkhodkhaknazarov.configurationupdater.core.model.Configuration

@Entity(tableName = ConfigurationModule.Database.TABLE_NAME)
data class ConfigurationModule(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Database.COL_ID)
    var id: Long,
    @ColumnInfo(name = Database.COL_CONFIGURATION_PASSWORD)
    var configurationPassword: String
)
{
    object Database {
        const val TABLE_NAME = "db_configuration"

        const val COL_ID = "id"
        const val COL_CONFIGURATION_PASSWORD = "configuration_password"
    }
}

fun Configuration.toConfigurationModel(): ConfigurationModule = ConfigurationModule(
        0,
        this.configurationPassword
    )

fun ConfigurationModule.toConfiguration() : Configuration =
    Configuration(this.id, this.configurationPassword)

