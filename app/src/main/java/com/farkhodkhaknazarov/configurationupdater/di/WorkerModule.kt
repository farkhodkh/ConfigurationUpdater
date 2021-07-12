package com.farkhodkhaknazarov.configurationupdater.di

import androidx.work.DelegatingWorkerFactory
import com.farkhodkhaknazarov.configurationupdater.feature.persistence.api.ConfigurationRepository
import com.farkhodkhaknazarov.configurationupdater.feature.persistence.api.SchedulerConfigurationRepository
import com.farkhodkhaknazarov.configurationupdater.feature.persistence.api.TerminalConfigurationRepository
import com.farkhodkhaknazarov.configurationupdater.feature.remote.config.executor.RemoteTerminalConfigExecutor
import com.farkhodkhaknazarov.configurationupdater.feature.remote.config.executor.RemoteTerminalConfigExecutorImpl
import com.farkhodkhaknazarov.configurationupdater.feature.remote.config.worker.RemoteConfigFactory
import com.farkhodkhaknazarov.configurationupdater.feature.remote.config.worker.RemoteConfigScheduler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WorkerModule {
    @Singleton
    @Provides
    fun providesRemoteConfigScheduler(schedulerConfigurationRepository: SchedulerConfigurationRepository): RemoteConfigScheduler =
        RemoteConfigScheduler(schedulerConfigurationRepository)

    @Singleton
    @Provides
    fun providesDelegatingWorkerFactory(
        @Named(DEPENDENCY_NAME_REMOTE_CONFIG_WORKER_FACTORY)
        remoteConfigFactory: RemoteConfigFactory
    ): DelegatingWorkerFactory =
        DelegatingWorkerFactory().apply {
            addFactory(remoteConfigFactory)
        }

    @Singleton
    @Provides
    @Named(DEPENDENCY_NAME_REMOTE_CONFIG_WORKER_FACTORY)
    fun providesWorkerFactory(
        remoteTerminalConfigExecutor: RemoteTerminalConfigExecutor
    ): RemoteConfigFactory =
        RemoteConfigFactory(remoteTerminalConfigExecutor)

    @Singleton
    @Provides
    fun providesRemoteConfigExecutor(
        configurationRepository: ConfigurationRepository,
        terminalConfigurationRepository: TerminalConfigurationRepository,
        schedulerSchedulerConfigurationRepository: SchedulerConfigurationRepository
    ): RemoteTerminalConfigExecutor =
        RemoteTerminalConfigExecutorImpl(
            configurationRepository,
            terminalConfigurationRepository,
            schedulerSchedulerConfigurationRepository
        )
}