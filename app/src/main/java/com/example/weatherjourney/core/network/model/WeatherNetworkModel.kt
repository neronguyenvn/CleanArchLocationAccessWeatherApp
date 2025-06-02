package com.example.weatherjourney.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherNetworkModel(
    val hourly: HourlyWeatherNetworkModel,
    val daily: DailyWeatherNetworkModel,
    val coordinate: CoordinateNetworkModel? = null,
)

@Serializable
data class HourlyWeatherNetworkModel(
    val time: List<Long>,

    @SerialName("temperature_2m")
    val temperatures: List<Double>,

    @SerialName("relativehumidity_2m")
    val humanities: List<Double>,

    @SerialName("weathercode")
    val weatherCodes: List<Int>,
)

@Serializable
data class DailyWeatherNetworkModel(
    val time: List<Long>,

    @SerialName("weathercode")
    val weatherCodes: List<Int>,

    @SerialName("temperature_2m_min")
    val minTemperatures: List<Double>,

    @SerialName("temperature_2m_max")
    val maxTemperatures: List<Double>,
)
