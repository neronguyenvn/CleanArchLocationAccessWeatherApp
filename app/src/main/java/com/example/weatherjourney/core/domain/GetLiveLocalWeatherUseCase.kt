package com.example.weatherjourney.core.domain

import com.example.weatherjourney.core.data.GpsRepository
import com.example.weatherjourney.core.data.LocationWithWeatherRepository
import com.example.weatherjourney.core.data.UserDataRepository
import com.example.weatherjourney.core.model.LocationWithWeather
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class GetLiveLocalWeatherUseCase @Inject constructor(
    private val gpsRepository: GpsRepository,
    private val locationWithWeatherRepository: LocationWithWeatherRepository,
    private val userDataRepository: UserDataRepository,
    private val convertUnitUseCase: ConvertUnitUseCase,
) {
    operator fun invoke(): Flow<LocationWithWeather?> {
        return gpsRepository.getGpsLocationStream().flatMapLatest { coordinate ->
            combine(
                locationWithWeatherRepository.getLocationWithWeatherStream(
                    coordinate = coordinate
                ),
                userDataRepository.userData,
            ) { locationWithWeather, userData ->

                if (locationWithWeather?.weather == null) {
                    return@combine locationWithWeather
                }

                locationWithWeather.copy(
                    weather = convertUnitUseCase(
                        weather = locationWithWeather.weather,
                        temperatureUnit = userData.temperatureUnit,
                    )
                )
            }
        }
    }
}
