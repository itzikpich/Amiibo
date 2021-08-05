package com.itzikpich.amiiboapiapp.di

import android.app.Application
import androidx.room.Room
import com.itzikpich.amiiboapiapp.db.AmiiboDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(application: Application) : AmiiboDatabase =
        Room.databaseBuilder(application, AmiiboDatabase::class.java, "amiibo-db").build()

}