package com.example.weatherjourney.core.data

import com.example.weatherjourney.core.model.Coordinate
import com.example.weatherjourney.core.model.LocationWithWeather
import kotlinx.coroutines.flow.Flow

interface LocationWithWeatherRepository {

    fun getLocationWithWeatherStream(coordinate: Coordinate): Flow<LocationWithWeather?>
}
