package com.example.weatherjourney.core.data.implementation

import com.example.weatherjourney.core.data.LocationWithWeatherRepository
import com.example.weatherjourney.core.data.store.LocationStore
import com.example.weatherjourney.core.data.store.WeatherStore
import com.example.weatherjourney.core.model.Coordinate
import com.example.weatherjourney.core.model.LocationWithWeather
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import org.mobilenativefoundation.store.store5.StoreReadRequest
import javax.inject.Inject

class OfflineFirstLocationWithWeatherRepository @Inject constructor(
    private val locationStore: LocationStore,
    private val weatherStore: WeatherStore,
) : LocationWithWeatherRepository {

    override fun getLocationWithWeatherStream(coordinate: Coordinate): Flow<LocationWithWeather?> {
        val readRequest = StoreReadRequest.cached(
            key = coordinate,
            refresh = false
        )
        return combine(
            locationStore.stream(readRequest),
            weatherStore.stream(readRequest),
        ) { location, weather ->
            if (location.dataOrNull() == null) {
                return@combine null
            }

            LocationWithWeather(
                location = location.requireData(),
                weather = weather.dataOrNull(),
            )
        }
    }
}
