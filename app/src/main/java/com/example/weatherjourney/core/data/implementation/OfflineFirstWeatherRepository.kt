package com.example.weatherjourney.core.data.implementation

import com.example.weatherjourney.core.data.WeatherRepository
import com.example.weatherjourney.core.database.LocationDao
import javax.inject.Inject

class OfflineFirstWeatherRepository @Inject constructor(
    private val locationDao: LocationDao,
) : WeatherRepository {

    override suspend fun refreshWeatherOfLocations() {
        locationDao.getAll().forEach { location ->
            refreshWeatherOfLocation()
        }
    }

    override suspend fun refreshWeatherOfLocation(locationId: Int) {
        locationDao.getById(locationId) ?: return
        refreshWeatherOfLocation()
    }

    private fun refreshWeatherOfLocation() {
    }
}
