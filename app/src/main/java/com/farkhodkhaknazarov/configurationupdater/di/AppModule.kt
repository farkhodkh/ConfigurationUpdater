package com.farkhodkhaknazarov.configurationupdater.di

import android.content.Context
import com.farkhodkhaknazarov.configurationupdater.api.TemInvokerExecutor
import com.farkhodkhaknazarov.configurationupdater.api.TemInvokerExecutorImpl
import com.farkhodkhaknazarov.configurationupdater.api.TerminalConfigurationsRepositoryFactory
import com.farkhodkhaknazarov.configurationupdater.feature.persistence.api.ConfigurationRepository
import com.farkhodkhaknazarov.configurationupdater.feature.persistence.api.SchedulerConfigurationRepository
import com.farkhodkhaknazarov.configurationupdater.feature.persistence.api.TerminalConfigurationRepository
import com.farkhodkhaknazarov.configurationupdater.feature.remote.config.executor.RemoteTerminalConfigExecutor
import com.farkhodkhaknazarov.configurationupdater.ui.viewmodel.ConfigurationFragmentViewModel
import com.farkhodkhaknazarov.configurationupdater.ui.viewmodel.HomeFragmentViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun providesConfigurationsRepositoryFactory(@ApplicationContext appContext: Context): TerminalConfigurationsRepositoryFactory =
        TerminalConfigurationsRepositoryFactory(appContext)

    @Provides
    fun providesConfigurationRepository(factory: TerminalConfigurationsRepositoryFactory): ConfigurationRepository =
        factory.createConfigurationRepository()

    @Provides
    fun providesTerminalConfigurationRepository(factory: TerminalConfigurationsRepositoryFactory): TerminalConfigurationRepository =
        factory.createTerminalConfigurationRepository()

    @Provides
    fun providesSchedulerConfigurationRepository(factory: TerminalConfigurationsRepositoryFactory): SchedulerConfigurationRepository =
        factory.createSchedulerConfigurationRepository()

    @Provides
    fun providesTemInvokerExecutor(): TemInvokerExecutor = TemInvokerExecutorImpl()

    @Provides
    fun providesHomeFragmentViewModel(
        temInvokerExecutor: TemInvokerExecutor,
        remoteTerminalConfigExecutor: RemoteTerminalConfigExecutor
    ): HomeFragmentViewModel =
        HomeFragmentViewModel(temInvokerExecutor, remoteTerminalConfigExecutor)


    @Provides
    fun providesManualFragmentViewModel(
        @ApplicationContext appContext: Context,
        terminalConfigExecutor: RemoteTerminalConfigExecutor
    ): ConfigurationFragmentViewModel =
        ConfigurationFragmentViewModel(appContext.applicationInfo.packageName, terminalConfigExecutor)
}