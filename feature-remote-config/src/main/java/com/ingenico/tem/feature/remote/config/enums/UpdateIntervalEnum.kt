package com.farkhodkhaknazarov.configurationupdater.feature.remote.config.enums

enum class UpdateIntervalEnum(val id: String, val minutes: Long) {
    MIN_15("0", 15L),
    MIN_20("1", 20L),
    MIN_25("2", 25L),
    MIN_30("3", 30L),
    MIN_35("4", 35L),
    MIN_40("5", 40L),
    MIN_45("6", 45L),
    MIN_50("7", 50L),
    MIN_55("8", 55L),
    MIN_60("9", 60L);

    companion object {
        fun getById(id: String): UpdateIntervalEnum? =
            values().filter { it.id.equals(id, true) }.firstOrNull()

        fun getListOfValues(): List<String> {
            val intervals = mutableListOf("")
            values().forEach {
                intervals.add(it.minutes.toString())
            }

            return intervals
        }
    }
}
