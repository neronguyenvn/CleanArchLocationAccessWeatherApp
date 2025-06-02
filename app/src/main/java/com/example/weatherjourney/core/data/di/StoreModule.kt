package com.example.weatherjourney.core.data.di

import com.example.weatherjourney.core.data.store.LocationStore
import com.example.weatherjourney.core.data.store.LocationStoreFactory
import com.example.weatherjourney.core.data.store.WeatherStore
import com.example.weatherjourney.core.data.store.WeatherStoreFactory
import com.example.weatherjourney.core.database.LocationDao
import com.example.weatherjourney.core.database.WeatherDao
import com.example.weatherjourney.core.network.NetworkDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class StoreModule {

    @Provides
    fun providesLocationStore(
        networkDataSource: NetworkDataSource,
        locationDao: LocationDao
    ): LocationStore {

        return LocationStoreFactory(
            networkDataSource = networkDataSource,
            locationDao = locationDao,
        ).create()
    }

    @Provides
    fun providesWeatherStore(
        networkDataSource: NetworkDataSource,
        weatherDao: WeatherDao,
    ): WeatherStore {

        return WeatherStoreFactory(
            networkDataSource = networkDataSource,
            weatherDao = weatherDao,
        ).create()
    }
}
