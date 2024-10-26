package com.example.weatherjourney.core.data

import com.example.weatherjourney.core.model.Location
import com.example.weatherjourney.core.model.LocationWithWeather
import kotlinx.coroutines.flow.Flow

typealias LocationId = Int

interface LocationRepository {

    fun getLocationsWithWeather(): Flow<List<LocationWithWeather>>

    fun getLocationWithWeather(id: Int): Flow<LocationWithWeather?>

    suspend fun saveAndRefreshLocation(location: Location): LocationId

    suspend fun deleteLocation(id: Int)

    suspend fun getLocationsByAddress(address: String): Flow<List<Location>>
}
