package com.farkhodkhaknazarov.configurationupdater.feature.persistence.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.farkhodkhaknazarov.configurationupdater.core.model.TerminalConfiguration

@Entity(tableName = TerminalConfigurationModule.Database.TABLE_NAME)
data class TerminalConfigurationModule(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Database.COL_ID)
    var id: Long,
    @ColumnInfo(name = Database.COL_PACKAGE_NAME)
    var packageName: String,
    @ColumnInfo(name = Database.COL_SERIAL_NUMBER)
    var serialNumber: String,
    @ColumnInfo(name = Database.COL_SERVER_URL)
    var serverUrl: String,
    @ColumnInfo(name = Database.COL_MUTUAL_TLS)
    var mutualTls: Boolean,
    @ColumnInfo(name = Database.COL_KEY_OPTION)
    var keyOption: Int,
    @ColumnInfo(name = Database.COL_CLIENT_KEY, typeAffinity = ColumnInfo.BLOB)
    var clientKey: ByteArray,
    @ColumnInfo(name = Database.COL_CLIENT_KEY_PASSWORD, typeAffinity = ColumnInfo.BLOB)
    var clientKeyPassword: ByteArray,
    @ColumnInfo(name = Database.COL_TRUST_CA, typeAffinity = ColumnInfo.BLOB)
    var trustCa: ByteArray,
    @ColumnInfo(name = Database.COL_TRUST_CA_PASSWORD, typeAffinity = ColumnInfo.BLOB)
    var trustCaPassword: ByteArray
) {
    object Database {
        const val TABLE_NAME = "terminal_configuration"

        const val COL_ID = "id"
        const val COL_PACKAGE_NAME = "package_name"
        const val COL_SERIAL_NUMBER = "serial_number"
        const val COL_SERVER_URL = "server_url"
        const val COL_MUTUAL_TLS = "mutual_tls"
        const val COL_KEY_OPTION = "key_option"
        const val COL_CLIENT_KEY = "client_key"
        const val COL_CLIENT_KEY_PASSWORD = "client_key_password"
        const val COL_TRUST_CA = "trust_ca"
        const val COL_TRUST_CA_PASSWORD = "trust_ca_password"
    }
}

fun TerminalConfigurationModule.toTerminalConfiguration(): TerminalConfiguration =
    TerminalConfiguration(
        this.id,
        this.packageName,
        this.serialNumber,
        this.serverUrl,
        this.mutualTls,
        this.keyOption,
        this.clientKey.toString(),
        this.clientKeyPassword.toString(),
        this.trustCa.toString(),
        this.trustCaPassword.toString()
    )

fun TerminalConfiguration.toTerminalConfigurationModule(): TerminalConfigurationModule =
    TerminalConfigurationModule(
        0,
        this.packageName,
        this.serialNumber,
        this.serverUrl,
        this.mutualTls,
        this.keyOption,
        this.clientKey.toByteArray(),
        this.clientKeyPassword.toByteArray(),
        this.trustCa.toByteArray(),
        this.trustCaPassword.toByteArray()
    )