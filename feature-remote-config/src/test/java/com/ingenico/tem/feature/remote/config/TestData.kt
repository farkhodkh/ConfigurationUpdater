package com.farkhodkhaknazarov.configurationupdater.feature.remote.config

import com.farkhodkhaknazarov.configurationupdater.core.model.SchedulerConfiguration
import com.farkhodkhaknazarov.configurationupdater.core.model.TerminalConfiguration
import com.farkhodkhaknazarov.configurationupdater.feature.remote.config.enums.UpdateIntervalEnum
import com.farkhodkhaknazarov.configurationupdater.feature.remote.config.model.ConfigurationAction


object TestData {
    const val fullXml: String = "" +
            "<Configurations>\n" +
            "    <configurationUpdateInterval parameter=\"configurationUpdateInterval\">0</configurationUpdateInterval>\n" +
            "    <configurationInvocationInterval parameter=\"configurationInvocationInterval\">1</configurationInvocationInterval>\n" +
            "    <deleteAllConfigurations parameter=\"deleteAllConfigurations\">1</deleteAllConfigurations>\n" +
            "    <deleteConfigurations parameter=\"deleteConfigurations\">\n" +
            "        <serialNumber parameter=\"serialNumber\">204GCA124129</serialNumber>\n" +
            "    </deleteConfigurations>\n" +
            "    <availableConfigurations parameter=\"availableConfigurations\">\n" +
            "        <serialNumber parameter=\"serialNumber\">204GCA124128</serialNumber>\n" +
            "        <serverUrl parameter=\"serverUrl\">dev.farkhod.ru</serverUrl>\n" +
            "        <mutualTls parameter=\"mutualTls\">0</mutualTls>\n" +
            "        <keyOption parameter=\"keyOption\">0</keyOption>\n" +
            "        <clientKey parameter=\"clientKey\">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</clientKey>\n" +
            "        <clientKeyPassword parameter=\"clientKeyPassword\">111</clientKeyPassword>\n" +
            "        <trustCa parameter=\"trustCa\">MIICyTCCAjKgAwIBAgIQAMhhMqEVlLjuBaORJSE30zANBgkqhkiG9w0BAQUFADBoMRQwEgYDVQQDDAtpbmdlbmljby5ydTERMA8GA1UECgwISW5nZW5pY28xDTALBgNVBAsMBHVzZXIxDTALBgNVBAwMBHVzZXIxHzAdBgkqhkiG9w0BCQEWEGluZm9AaW5nZW5pY28ucnUwHhcNMjEwNDA1MTAwNTM0WhcNMjIwNDEyMTAwNTM0WjBoMRQwEgYDVQQDDAtpbmdlbmljby5ydTERMA8GA1UECgwISW5nZW5pY28xDTALBgNVBAsMBHVzZXIxDTALBgNVBAwMBHVzZXIxHzAdBgkqhkiG9w0BCQEWEGluZm9AaW5nZW5pY28ucnUwgZ8wDQYJKoZIhvcNAQEBBQADgY0AMIGJAoGBAMUn0mGHwO5S0g9lVG2SdM7B2QA5CsGlUtGqwE2Iy+VsPSvJWk7rRwfrNUEau/SKcZVIIwqnsSanFzPyyiOyiubzyPdakSM71EonPpLRcwhesg4gT7dQI6kx4HTqmUXPR/g6JJg1JeSl8Pcqk/QatljD5NUXo0NYLN5OuKw7zl/tAgMBAAGjdDByMFkGCisGAQQBgjcKAwwESzBJgBSRLPNhqHuSQge5017YXCD1bnhKZaEfpB0wGzEZMBcGA1UEAwwQRURYX0ZLSEFLTkFaQVJPVoIQAMhhMqEVlLjuBaORJSE30zAVBgNVHSUEDjAMBgorBgEEAYI3CgMMMA0GCSqGSIb3DQEBBQUAA4GBAKwP6Y1HGUf61lfhGoWsh4jwd0LfIYI2fGhrTbBhawjkLCvEADxNZlnv84avlShEMUOZodAfGsmIprdEYambKft1vjSstrZAAHyGNllL5FBKZThG0BLcgAG71IRbTIt8DfjVyBT0WC/AOtNAJFkl1W7pThv/eCoiXTeH2qQH/Hnb</trustCa>\n" +
            "        <trustCaPassword parameter=\"trustCaPassword\">111</trustCaPassword>\n" +
            "    </availableConfigurations>\n" +
            "    <availableConfigurations parameter=\"availableConfigurations\">\n" +
            "        <serialNumber parameter=\"serialNumber\">204GCA124129</serialNumber>\n" +
            "        <serverUrl parameter=\"serverUrl\">http://dev.farkhod.ru/</serverUrl>\n" +
            "        <mutualTls parameter=\"mutualTls\">0</mutualTls>\n" +
            "        <keyOption parameter=\"keyOption\">0</keyOption>\n" +
            "        <clientKey parameter=\"clientKey\"></clientKey>\n" +
            "        <clientKeyPassword parameter=\"clientKeyPassword\"></clientKeyPassword>\n" +
            "        <trustCa parameter=\"trustCa\"></trustCa>\n" +
            "        <trustCaPassword parameter=\"trustCaPassword\"></trustCaPassword>\n" +
            "    </availableConfigurations>" +
            "</Configurations>\n"

    fun deleteAllConfigurations(): ConfigurationAction.DeleteAllConfigurations =
        ConfigurationAction.DeleteAllConfigurations(true)

    fun getDeleteConfigurations(): ConfigurationAction.DeleteConfigurations =
        ConfigurationAction.DeleteConfigurations(listOf(
            TerminalConfiguration(0, "", "204GCA124129")
        ))

    fun getConfigurationUpdateInterval(): ConfigurationAction.ConfigurationUpdateInterval =
        ConfigurationAction.ConfigurationUpdateInterval(SchedulerConfiguration(0, UpdateIntervalEnum.getById("0")?.minutes ?: 0))

    fun getConfigurationInvocationInterval(): ConfigurationAction.ConfigurationInvocationInterval =
        ConfigurationAction.ConfigurationInvocationInterval(SchedulerConfiguration(0,0, UpdateIntervalEnum.getById("1")?.minutes ?: 0))

    fun getAddConfigurations_0601(): ConfigurationAction.AddConfigurations =
        ConfigurationAction.AddConfigurations(
            listOf(
                TerminalConfiguration(
                    0,
                    "",
                    "204GCA124129",
                    "http://dev.farkhod.ru/",
                    false,
                    0,
                    "",
                    "",
                    "",
                    ""
                )
            )
        )

    fun getAddConfigurations_0600(): ConfigurationAction.AddConfigurations =
        ConfigurationAction.AddConfigurations(
            listOf(
                TerminalConfiguration(
                    0,
                    "",
                    "204GCA124128",
                    "dev.farkhod.ru",
                    false,
                    0,
                    "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",
                    "111",
                    "MIICyTCCAjKgAwIBAgIQAMhhMqEVlLjuBaORJSE30zANBgkqhkiG9w0BAQUFADBoMRQwEgYDVQQDDAtpbmdlbmljby5ydTERMA8GA1UECgwISW5nZW5pY28xDTALBgNVBAsMBHVzZXIxDTALBgNVBAwMBHVzZXIxHzAdBgkqhkiG9w0BCQEWEGluZm9AaW5nZW5pY28ucnUwHhcNMjEwNDA1MTAwNTM0WhcNMjIwNDEyMTAwNTM0WjBoMRQwEgYDVQQDDAtpbmdlbmljby5ydTERMA8GA1UECgwISW5nZW5pY28xDTALBgNVBAsMBHVzZXIxDTALBgNVBAwMBHVzZXIxHzAdBgkqhkiG9w0BCQEWEGluZm9AaW5nZW5pY28ucnUwgZ8wDQYJKoZIhvcNAQEBBQADgY0AMIGJAoGBAMUn0mGHwO5S0g9lVG2SdM7B2QA5CsGlUtGqwE2Iy+VsPSvJWk7rRwfrNUEau/SKcZVIIwqnsSanFzPyyiOyiubzyPdakSM71EonPpLRcwhesg4gT7dQI6kx4HTqmUXPR/g6JJg1JeSl8Pcqk/QatljD5NUXo0NYLN5OuKw7zl/tAgMBAAGjdDByMFkGCisGAQQBgjcKAwwESzBJgBSRLPNhqHuSQge5017YXCD1bnhKZaEfpB0wGzEZMBcGA1UEAwwQRURYX0ZLSEFLTkFaQVJPVoIQAMhhMqEVlLjuBaORJSE30zAVBgNVHSUEDjAMBgorBgEEAYI3CgMMMA0GCSqGSIb3DQEBBQUAA4GBAKwP6Y1HGUf61lfhGoWsh4jwd0LfIYI2fGhrTbBhawjkLCvEADxNZlnv84avlShEMUOZodAfGsmIprdEYambKft1vjSstrZAAHyGNllL5FBKZThG0BLcgAG71IRbTIt8DfjVyBT0WC/AOtNAJFkl1W7pThv/eCoiXTeH2qQH/Hnb",
                    "111"
                )
            )
        )
}