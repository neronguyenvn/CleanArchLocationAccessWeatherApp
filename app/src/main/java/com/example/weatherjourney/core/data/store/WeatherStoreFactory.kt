package com.example.weatherjourney.core.data.store

import com.example.weatherjourney.core.data.mapper.asEntity
import com.example.weatherjourney.core.database.WeatherDao
import com.example.weatherjourney.core.database.model.WeatherWithDetails
import com.example.weatherjourney.core.database.model.asWeather
import com.example.weatherjourney.core.model.Coordinate
import com.example.weatherjourney.core.model.Weather
import com.example.weatherjourney.core.network.NetworkDataSource
import com.example.weatherjourney.core.network.model.WeatherNetworkModel
import kotlinx.coroutines.flow.map
import org.mobilenativefoundation.store.store5.Converter
import org.mobilenativefoundation.store.store5.Fetcher
import org.mobilenativefoundation.store.store5.SourceOfTruth
import org.mobilenativefoundation.store.store5.Store
import org.mobilenativefoundation.store.store5.StoreBuilder

typealias WeatherStore = Store<Coordinate, Weather>

class WeatherStoreFactory(
    private val networkDataSource: NetworkDataSource,
    private val weatherDao: WeatherDao,
) {
    fun create(): WeatherStore {
        return StoreBuilder.from(
            fetcher = createFetcher(),
            sourceOfTruth = createSourceOfTruth(),
            converter = createConverter()
        ).build()
    }

    private fun createFetcher(): Fetcher<Coordinate, WeatherNetworkModel> =
        Fetcher.of { coordinate ->
            networkDataSource.getWeather(coordinate)
        }

    private fun createSourceOfTruth(): SourceOfTruth<Coordinate, WeatherWithDetails, Weather> =
        SourceOfTruth.of(
            reader = { coordinate ->
                weatherDao
                    .observe(coordinate.latitude, coordinate.longitude)
                    .map { it.asWeather() }
            },
            writer = { _, weatherWithDetails ->
                val weatherId = weatherDao.insert(weatherWithDetails.weather)
                weatherDao.insertHourly(
                    weatherWithDetails.hourly.copy(weatherId = weatherId.toInt())
                )
                weatherDao.insertDaily(
                    weatherWithDetails.daily.copy(weatherId = weatherId.toInt())
                )
            }
        )

    private fun createConverter(): Converter<WeatherNetworkModel, WeatherWithDetails, Weather> =
        Converter.Builder<WeatherNetworkModel, WeatherWithDetails, Weather>()
            .fromOutputToLocal { weather -> weather.asEntity() }
            .fromNetworkToLocal { weather -> weather.asEntity() }
            .build()
}
