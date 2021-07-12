package com.farkhodkhaknazarov.configurationupdater.feature.persistence.room.dao

import androidx.room.*
import com.farkhodkhaknazarov.configurationupdater.feature.persistence.room.model.SchedulerConfigurationModule

@Dao
interface SchedulerConfigurationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(config: SchedulerConfigurationModule): Long

    @Delete
    suspend fun delete(config: SchedulerConfigurationModule)

    @Update
    suspend fun update(config: SchedulerConfigurationModule)

    @Query("DELETE FROM ${SchedulerConfigurationModule.Database.TABLE_NAME}")
    suspend fun deleteAll()

    @Query("UPDATE ${SchedulerConfigurationModule.Database.TABLE_NAME} SET ${SchedulerConfigurationModule.Database.COL_INVOCATION_INTERVAL} = :invocationInterval")
    suspend fun updateConfigurationInvocationInterval(invocationInterval: Long)

    @Query("UPDATE ${SchedulerConfigurationModule.Database.TABLE_NAME} SET ${SchedulerConfigurationModule.Database.COL_CONFIGURATION_UPDATE_INTERVAL} = :updateInterval")
    suspend fun updateConfigurationUpdateInterval(updateInterval: Long)

    @Query("SELECT * FROM ${SchedulerConfigurationModule.Database.TABLE_NAME}")
    suspend fun getAllLines(): List<SchedulerConfigurationModule>

    @Query("SELECT * FROM ${SchedulerConfigurationModule.Database.TABLE_NAME} ORDER BY ${SchedulerConfigurationModule.Database.COL_ID} Desc LIMIT 1")
    suspend fun getLastScheduler(): SchedulerConfigurationModule?
}