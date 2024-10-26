package com.example.weatherjourney.core.database.di

import android.content.Context
import androidx.room.Room
import com.example.weatherjourney.core.database.room.RoomWeatherDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): RoomWeatherDatabase = Room.databaseBuilder(
        context,
        RoomWeatherDatabase::class.java,
        "wtn-database",
    ).build()

    @Provides
    fun providesLocationDao(
        database: RoomWeatherDatabase
    ) = database.locationDao()

    @Provides
    fun providesWeatherDao(
        database: RoomWeatherDatabase
    ) = database.weatherDao()
}