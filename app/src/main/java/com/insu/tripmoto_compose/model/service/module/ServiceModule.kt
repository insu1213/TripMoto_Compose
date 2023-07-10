package com.insu.tripmoto_compose.model.service.module

import com.insu.tripmoto_compose.model.service.AccountService
import com.insu.tripmoto_compose.model.service.LogService
import com.insu.tripmoto_compose.model.service.impl.AccountServiceImpl
import com.insu.tripmoto_compose.model.service.impl.LogServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {
    @Binds abstract fun provideAccountService(impl: AccountServiceImpl): AccountService

    @Binds abstract fun provideLogService(impl: LogServiceImpl): LogService

//    @Binds abstract fun provideStorageService(impl: StorageServiceImpl): StorageService
//
//    @Binds
//    abstract fun provideConfigurationService(impl: ConfigurationServiceImpl): ConfigurationService
}