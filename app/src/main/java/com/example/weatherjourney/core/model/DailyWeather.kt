package com.example.weatherjourney.core.model

data class DailyWeather(
    val epochTime: Long,
    val maxTemp: Double,
    val minTemp: Double,
    val weatherType: WeatherType,
)
