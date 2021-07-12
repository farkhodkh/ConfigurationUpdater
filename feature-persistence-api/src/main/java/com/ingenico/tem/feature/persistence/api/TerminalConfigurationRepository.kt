package com.farkhodkhaknazarov.configurationupdater.feature.persistence.api

import com.farkhodkhaknazarov.configurationupdater.core.model.TerminalConfiguration

interface TerminalConfigurationRepository {

    /**
     * Inserts new [TerminalConfiguration] entity.
     *
     * @param key [TerminalConfiguration] object to insert.
     */
    suspend fun insert(configuration: TerminalConfiguration)

    /**
     * Updates existing [TerminalConfiguration] entity.
     *
     * @param key [TerminalConfiguration] object to update.
     */
    suspend fun update(configuration: TerminalConfiguration)

    /**
     * Deletes existing [TerminalConfiguration] entity.
     *
     * @param key [TerminalConfiguration] object to delete.
     */
    suspend fun delete(configuration: TerminalConfiguration)

    /**
     * Deletes existing [TerminalConfiguration] entity by terminal ID (TID).
     *
     * @param key [TID] object to delete.
     */
    suspend fun delete(tid: String)

    /**
     * Deletes all existing [TerminalConfiguration] entity.
     *
     */
    suspend fun deleteAll()

    /**
     * Method gets list of all [TerminalConfiguration]
     */
    suspend fun getAll(): List<TerminalConfiguration>
}