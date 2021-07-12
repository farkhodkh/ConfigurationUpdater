package com.farkhodkhaknazarov.configurationupdater.feature.remote.config.utils

import com.farkhodkhaknazarov.configurationupdater.feature.remote.config.TestData
import com.farkhodkhaknazarov.configurationupdater.feature.remote.config.TestData.deleteAllConfigurations
import com.farkhodkhaknazarov.configurationupdater.feature.remote.config.TestData.getAddConfigurations_0600
import com.farkhodkhaknazarov.configurationupdater.feature.remote.config.TestData.getAddConfigurations_0601
import com.farkhodkhaknazarov.configurationupdater.feature.remote.config.TestData.getConfigurationInvocationInterval
import com.farkhodkhaknazarov.configurationupdater.feature.remote.config.TestData.getConfigurationUpdateInterval
import com.farkhodkhaknazarov.configurationupdater.feature.remote.config.TestData.getDeleteConfigurations
import com.farkhodkhaknazarov.configurationupdater.feature.remote.config.model.ConfigurationAction
import com.farkhodkhaknazarov.configurationupdater.feature.remote.config.model.ConfigurationModel
import com.farkhodkhaknazarov.configurationupdater.feature.remote.config.util.ParametersParser
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class ParametersParserTest {
    @Test
    fun `parse whole xml`() {
        runBlockingTest {
            val configuration: ConfigurationModel = TestData.fullXml
                .toByteArray(Charsets.UTF_8)
                .inputStream()
                .use {
                    ParametersParser("").parse(it)
                }

            Assert.assertTrue(configuration.actions.size == 6)


            configuration.actions.forEach {
                when (it) {
                    is ConfigurationAction.Default -> {

                    }
                    is ConfigurationAction.ConfigurationUpdateInterval -> {
                        Assert.assertEquals(it.schedulerConfiguration, getConfigurationUpdateInterval().schedulerConfiguration)
                    }
                    is ConfigurationAction.ConfigurationInvocationInterval -> {
                        Assert.assertEquals(it.schedulerConfiguration, getConfigurationInvocationInterval().schedulerConfiguration)
                    }
                    is ConfigurationAction.AddConfigurations -> {
                        Assert.assertTrue(it.list.size > 0)

                        if (it.list[0].serialNumber=="204GCA124129") {
                            Assert.assertEquals(it.list, getAddConfigurations_0601().list)
                        } else if (it.list[0].serialNumber=="204GCA124128") {
                            Assert.assertEquals(it.list, getAddConfigurations_0600().list)
                        }
                    }
                    is ConfigurationAction.DeleteAllConfigurations -> {
                        Assert.assertEquals(it.deleteAll, deleteAllConfigurations().deleteAll)
                    }
                    is ConfigurationAction.DeleteConfigurations -> {
                        Assert.assertEquals(it.list, getDeleteConfigurations().list)
                    }
                }
            }
        }
    }
}