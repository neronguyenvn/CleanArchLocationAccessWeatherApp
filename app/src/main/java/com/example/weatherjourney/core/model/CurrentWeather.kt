package com.example.weatherjourney.core.model

data class CurrentWeather(
    val temp: Double,
    val humidity: Double,
    val weatherType: WeatherType,
)
