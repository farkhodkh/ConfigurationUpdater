package com.farkhodkhaknazarov.configurationupdater.feature.persistence.room.dao

import androidx.room.*
import com.farkhodkhaknazarov.configurationupdater.feature.persistence.room.model.TerminalConfigurationModule

@Dao
internal interface TerminalConfigurationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(config: TerminalConfigurationModule): Long

    @Delete
    suspend fun delete(config: TerminalConfigurationModule)

    @Query("DELETE FROM ${TerminalConfigurationModule.Database.TABLE_NAME} WHERE ${TerminalConfigurationModule.Database.COL_SERIAL_NUMBER} = :serialNumber")
    suspend fun deleteBySerialNumber(serialNumber: String)

    @Update
    suspend fun update(config: TerminalConfigurationModule)

    @Query("DELETE FROM ${TerminalConfigurationModule.Database.TABLE_NAME}")
    fun deleteAll()

    @Query("Select * FROM ${TerminalConfigurationModule.Database.TABLE_NAME}")
    fun getAll(): List<TerminalConfigurationModule>
}