package com.farkhodkhaknazarov.configurationupdater.feature.persistence.room

import androidx.room.TypeConverter

internal class Converters {
    @TypeConverter
    fun stringToByteArray(value: String?): ByteArray? =
        value?.toByteArray()

    @TypeConverter
    fun byteArrayToString(value: ByteArray?): String? =
        value?.let { it.toString() }
}