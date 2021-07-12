package com.farkhodkhaknazarov.configurationupdater.feature.persistence.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.farkhodkhaknazarov.configurationupdater.feature.persistence.room.model.ConfigurationModule

@Dao
internal interface ConfigurationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(config: ConfigurationModule): Long

    @Query("DELETE FROM ${ConfigurationModule.Database.TABLE_NAME}")
    suspend fun deleteAll()

    @Query("SELECT * FROM ${ConfigurationModule.Database.TABLE_NAME} ORDER BY ${ConfigurationModule.Database.COL_ID} Desc LIMIT 1")
    suspend fun getLastConfiguration(): ConfigurationModule?
}