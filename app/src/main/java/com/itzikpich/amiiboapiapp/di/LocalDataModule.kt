package com.itzikpich.amiiboapiapp.di

import com.itzikpich.amiiboapiapp.data.local.LocalDataSource
import com.itzikpich.amiiboapiapp.data.local.LocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalDataModule {

    @Binds
    abstract fun provideLocalDataSource(localDataSourceImpl: LocalDataSourceImpl): LocalDataSource

}