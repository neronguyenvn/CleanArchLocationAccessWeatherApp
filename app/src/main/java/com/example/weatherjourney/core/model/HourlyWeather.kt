package com.example.weatherjourney.core.model

data class HourlyWeather(
    val epochTime: Long,
    val temp: Double,
    val humanity: Double,
    val weatherType: WeatherType,
)
