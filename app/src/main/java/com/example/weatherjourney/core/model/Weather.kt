package com.example.weatherjourney.core.model

data class Weather(
    val coordinate: Coordinate,
    val current: CurrentWeather,
    val dailyForecasts: List<DailyWeather>,
    val hourlyForecasts: List<HourlyWeather>,
    val id: Int = 0,
)

fun Weather.convertTemperature(convertMethod: (Double) -> Double) = this.copy(
    current = current.copy(
        temp = convertMethod(current.temp),
    ),
    dailyForecasts = dailyForecasts.map {
        it.copy(
            maxTemp = convertMethod(it.maxTemp),
            minTemp = convertMethod(it.minTemp),
        )
    },
    hourlyForecasts = hourlyForecasts.map {
        it.copy(
            temp = convertMethod(it.temp),
        )
    },
)
